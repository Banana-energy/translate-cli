<template>
  <div>
    <table-box :list-title="'翻译文件列表'">
      <template v-slot:header>
        <Button class="filter-item" type="primary" icon="ios-search-outline">
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
              <Button icon="ios-cloud-upload-outline">导入JSON</Button>
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
                  <Upload
                    :show-upload-list="false"
                    :before-upload="(file)=>uploadJs(file,item.name,item.lang)"
                    action="//jsonplaceholder.typicode.com/posts/"
                  >
                    <Button icon="ios-cloud-upload-outline">导入JSON</Button>
                  </Upload>
                </li>
                <li>
                  <Button type="text">删除</Button>
                </li>
              </template>
            </ListItem>
          </List>
        </Scroll>
      </template>
    </table-box>
    <TranslateTable :name="name" />
  </div>
</template>

<script>
import { getDocument, loadJs } from '@/api/translate/translate'
import TranslateTable from '@/views/translate/TranslateTable'

export default {
  name: 'Index',
  components: {
    TranslateTable
  },
  data() {
    return {
      loading: true,
      disabled: true,
      docList: [],
      json: {},
      listQuery: {},
      docQuery: {},
      name: '',
      langList: [{
        label: '中文',
        value: 'zh'
      }],
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
    this.getDocList()
  },
  methods: {
    async getDocList() {
      this.loading = true
      const { data } = await getDocument()
      for (let i = 0; i < data.length; i++) {
        data[i].id = i + 1
      }
      this.docList = data
      this.loading = false
    },
    uploadJs(file, name, lang) {
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
          this.getFileContent(evt).then(() => {
            const params = {
              json: this.json,
              lang,
              name
            }
            loadJs(params)
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

::v-deep .ivu-list-item-action > li {
  text-align: left;
}
</style>
