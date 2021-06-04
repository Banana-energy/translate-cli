<template>
  <Modal
    :value="showModal"
    :mask-closable="false"
    @on-ok="handleSubmit"
    @on-cancel="handleClose"
  >
    <div class="title">{{ row.name ? '编辑' : '添加' }}</div>
    <div class="container">
      <Form ref="editForm" label-position="top" :rules="rules" :model="listQuery">
        <FormItem v-if="row.hashKey" label="Hash Key">
          <Input v-model="listQuery.key" clearable type="text" disabled />
        </FormItem>
        <FormItem v-if="!row.name" label="翻译文档名">
          <Input v-model="listQuery.name" clearable placeholder="请输入翻译文档名" type="text" />
        </FormItem>
        <FormItem prop="words" label="中文">
          <Input v-model="listQuery.words" :disabled="!!row.name" clearable type="text" placeholder="请输入中文" />
        </FormItem>
        <FormItem label="翻译目标语言">
          <Select v-model="listQuery.lang" placeholder="请选择目标语言" clearable>
            <Option v-for="item in langList" :key="item.value" :value="item.value">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="翻译">
          <Input v-model="listQuery.value" clearable type="text" placeholder="请输入翻译" />
        </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="handleClose">取消</Button>
      <Button type="primary" :loading="loading" @click="handleSubmit('editForm')">确定</Button>
    </div>
  </Modal>
</template>

<script>
import { add, edit } from '@/api/translate/translate'

export default {
  name: 'EditModel',
  props: {
    row: {
      type: Object,
      default: () => {
        return {}
      }
    },
    showModal: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      listQuery: {
        words: '',
        key: '',
        value: '',
        lang: '',
        name: ''
      },
      rules: {
        words: [{
          required: true,
          message: '请填写中文'
        }]
      },
      loading: false
    }
  },
  computed: {
    langList() {
      const list = []
      this.$store.getters.languageList.map((val) => {
        list.push(val)
      })
      list.splice(0, 1)
      return list
    }
  },
  watch: {
    row(value) {
      this.listQuery.key = value.hashKey
      this.listQuery.words = value.zh
      this.listQuery.name = value.name
      this.listQuery.value = ''
      this.listQuery.lang = ''
    }
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate(async(valid) => {
        if (valid) {
          try {
            this.loading = true
            let data
            if (this.row.name) {
              data = await edit(this.listQuery)
            } else {
              data = await add(this.listQuery)
            }
            this.handleClose()
            this.$Message.success({
              content: data.rtnMessage,
              duration: 5
            })
            this.$emit('refurbish')
            this.$emit('refresh')
          } finally {
            this.loading = false
          }
        }
      })
    },
    handleClose() {
      this.$emit('update:show-modal', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.title {
  font-size: 24px;
  font-weight: bold;
}

.container {
  margin: 10px 35px;
}
</style>
