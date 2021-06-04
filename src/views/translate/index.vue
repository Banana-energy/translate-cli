<template>
  <div>
    <table-box :list-title="'翻译文件列表'">
      <template v-slot:header>
        <Button class="filter-item" type="primary" icon="ios-search-outline" @click="getDocList">
          搜索
        </Button>
      </template>
      <template v-slot:search>
        <Form ref="search-form" label-position="left" :model="listQuery" :label-width="55" inline>
          <FormItem label="文档名">
            <Input v-model="listQuery.name" clearable type="text" placeholder="请输入文档名" />
          </FormItem>
        </Form>
      </template>
      <template v-slot:button>
        <Form
          ref="translate-form"
          class="document-form"
          label-position="right"
          :model="docQuery"
          :label-width="105"
          inline
        >
          <FormItem label="文档名">
            <Input v-model="docQuery.name" clearable type="text" placeholder="请输入文档名" />
          </FormItem>
          <FormItem label="翻译目标语言">
            <Select v-model="docQuery.lang" placeholder="请选择目标语言" clearable style="width:200px">
              <Option v-for="item in langList" :key="item.value" :value="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem class="document-form-button">
            <Upload
              :show-upload-list="false"
              :before-upload="(file)=>uploadJs(file,docQuery.name,docQuery.lang)"
              action="//jsonplaceholder.typicode.com/posts/"
            >
              <Button :loading="uploading" class="upload-button" icon="ios-cloud-upload">导入JSON</Button>
            </Upload>
          </FormItem>
        </Form>
      </template>
      <template v-slot:list>
        <Scroll v-if="docList.length" :on-reach-bottom="getDocList">
          <List :loading="loading">
            <ListItem v-for="item in docList" :key="item.id">
              <ListItemMeta
                avatar="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
                :title="item.name"
              />
              <template slot="action">
                <li>
                  <Button @click="changeList(item.name)">查看</Button>
                </li>
                <li>
                  <Select v-model="item.lang" placeholder="请选择目标语言" clearable style="width:200px">
                    <Option v-for="lang in langList" :key="lang.value" :value="lang.value">{{ lang.label }}</Option>
                  </Select>
                </li>
                <li>
                  <Button
                    class="download-button"
                    :loading="item.loading"
                    icon="ios-cloud-download"
                    @click="exportJson(item.name,item.lang,item)"
                  >
                    导出JSON
                  </Button>
                </li>
                <li>
                  <Upload
                    :show-upload-list="false"
                    :before-upload="(file)=>uploadJs(file,item.name,item.lang, item)"
                    action="//jsonplaceholder.typicode.com/posts/"
                  >
                    <Button class="upload-button" :loading="item.uploading" icon="ios-cloud-upload">导入JSON</Button>
                  </Upload>
                </li>
                <li>
                  <Button class="delete-button" type="text" @click="deleteDoc(item.name)">删除</Button>
                </li>
              </template>
            </ListItem>
          </List>
        </Scroll>
      </template>
    </table-box>
    <TranslateTable ref="translate-table" :name="name" @refresh="getDocList" />
  </div>
</template>

<script>
import { deleteDocument, exportToJson, getDocument, loadJs } from '@/api/translate/translate'
import TranslateTable from '@/views/translate/TranslateTable'

export default {
  name: 'Index',
  components: {
    TranslateTable
  },
  data() {
    return {
      loading: true,
      uploading: false,
      disabled: true,
      docList: [],
      json: {},
      // 文档名称查询
      listQuery: {
        name: ''
      },
      docQuery: {},
      // 改变表格里的文档名称
      name: '',
      langList: [],
      columns: [{
        title: 'ID',
        key: 'id',
        align: 'center'
      }, {
        title: '翻译文档名',
        key: 'name',
        align: 'center'
      }, {
        title: '操作',
        slot: 'action',
        width: 150,
        align: 'center'
      }]
    }
  },
  mounted() {
    this.getLangList()
    this.getDocList()
  },
  methods: {
    async getLangList() {
      this.langList = await this.$store.dispatch('translate/getLangList')
    },
    async getDocList() {
      this.loading = true
      const { data } = await getDocument(this.listQuery)
      data.map((val) => {
        val.name = val.documentName
        val.downloading = false
        val.uploading = false
      })
      this.docList = data
      this.loading = false
    },
    deepClone(obj) {
      // 传递进来的如果不是对象，则无需处理，直接返回原始的值即可（一般Symbol和Function也不会进行处理的）
      if (obj === null) return null
      if (typeof obj !== 'object') return obj
      // 过滤掉特殊的对象（正则对象或者日期对象）：直接使用原始值创建当前类的一个新的实例即可，这样克隆后的是新的实例，但是值和之前一样
      if (obj instanceof RegExp) return new RegExp(obj)
      if (obj instanceof Date) return new Date(obj)
      // 如果传递的是数组或者对象，我们需要创建一个新的数组或者对象，用来存储原始的数据
      // obj.constructor 获取当前值的构造器（Array/Object）
      const cloneObj = new obj.constructor()
      for (const key in obj) {
        // 循环原始数据中的每一项，把每一项赋值给新的对象
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
          cloneObj[key] = this.deepClone(obj[key])
        } else {
          break
        }
      }
      return cloneObj
    },
    deepAssign(sObj, eObj) {
      const cObj = this.deepClone(sObj)
      // 再拿OBJ2替换OBJ中的每一项
      for (const key in eObj) {
        if (Object.prototype.hasOwnProperty.call(eObj, key)) {
          const v2 = eObj[key]
          const v1 = cObj[key]
          // 如果OBJ2遍历的当前项是个对象，并且对应的OBJ这项也是一个对象，此时不能直接替换，需要把两个对象重新合并一下，合并后的最新结果赋值给新对象中的这一项
          if (typeof v1 === 'object' && typeof v2 === 'object') {
            cObj[key] = this.deepAssign(v1, v2)
            continue
          }
          cObj[key] = v2
        }
      }
      return cObj
    },
    getObj(arr, value, json = {}) {
      if (arr.length !== 1) {
        const obj = {}
        obj[arr[arr.length - 1]] = value
        arr.pop()
        const temp = {}
        Object.assign(temp, obj)
        this.getObj(arr, temp, json)
      } else {
        json[arr[arr.length - 1]] = value
      }
      return json
    },
    async exportJson(name, lang, item) {
      if (!name || !lang) {
        this.$Message.error({
          content: '请选择要导出的语言',
          background: true,
          duration: 5
        })
        return false
      }
      item.downloading = true
      try {
        const { data } = await exportToJson({ name })
        const js = {}
        data.map((val) => {
          const keys = val.hashKey.split('.')
          const obj = this.getObj(keys, val[lang])
          for (const objKey in obj) {
            if (js[objKey] && Object.keys(js[objKey]).length !== 0) {
              // js[objKey]是对象且里面已经有值
              js[objKey] = this.deepAssign(js[objKey], obj[objKey])
            } else {
              js[objKey] = obj[objKey]
            }
          }
        })
        const a = document.createElement('a')
        a.download = 'translate.json'
        const blob = new Blob([JSON.stringify(js)])
        a.href = URL.createObjectURL(blob)
        document.body.append(a) // 修复firefox中无法触发click
        a.click()
        document.body.removeChild(a)
      } finally {
        item.downloading = false
      }
    },
    uploadJs(file, name, lang, item) {
      if (!name || !lang) {
        this.$Message.error({
          content: '请输入文档名称并选择目标语言',
          background: true,
          duration: 5
        })
        return false
      }
      const reader = new FileReader()
      reader.readAsText(file)
      reader.onloadend = (evt) => {
        if (evt.target.readyState === FileReader.DONE) {
          this.getFileContent(evt).then(async() => {
            try {
              if (item) {
                item.uploading = true
              } else {
                this.uploading = true
              }
              const params = {
                json: this.json,
                lang,
                name
              }
              const data = await loadJs(params)
              this.$Message.success({
                content: data.rtnMessage,
                duration: 5,
                background: true
              })
              await this.getDocList()
              await this.$refs['translate-table'].getList()
            } finally {
              if (item) {
                item.uploading = false
              } else {
                this.uploading = false
              }
            }
          })
        }
      }
    },
    map(obj, str = '') {
      const keys = Object.keys(obj)
      for (let i = 0; i < keys.length; i++) {
        let key = str + keys[i] + '.'
        if (typeof obj[keys[i]] === 'object') {
          this.map(obj[keys[i]], key)
        } else {
          key = key.substr(0, key.length - 1)
          this.json[key] = obj[keys[i]]
        }
      }
    },
    getFileContent(evt) {
      const reg = /module.exports = /g
      const regExp = /export default/g
      let str = evt.target.result
      str = str.replace(reg, '').replace(regExp, '')
      // eslint-disable-next-line
      let obj = eval('(' + str + ')')
      const json = JSON.stringify(obj)
      obj = JSON.parse(json)
      this.map(obj)
      return new Promise(resolve => {
        resolve()
      })
    },
    changeList(name) {
      this.name = name
    },
    async deleteDoc(name) {
      const { resultCode, rtnMessage } = await deleteDocument({ name })
      if (resultCode === 200) {
        this.$Message.success({
          content: rtnMessage,
          duration: 5,
          background: true
        })
        await this.getDocList()
        await this.$refs['translate-table'].getList()
      } else {
        this.$Message.error({
          content: rtnMessage,
          duration: 5,
          background: true
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.document-form {
  ::v-deep .ivu-form-item {
    margin-bottom: 0;
  }

  .document-form-button {
    ::v-deep .ivu-form-item-content {
      margin-left: 0 !important;
    }
  }
}

.upload-button {
  color: #2d8cf0
}

.download-button {
  color: #19be6b;
}

.delete-button {
  color: #ed4014;

  ::v-deep {
    span {
      &:hover {
        text-decoration: underline;
      }
    }
  }

  &:focus {
    box-shadow: none
  }
}

::v-deep .ivu-list-item-action > li {
  text-align: left;
}
</style>
