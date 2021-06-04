<template>
  <Modal
    :value="showModal"
    :mask-closable="false"
    @on-ok="handleSubmit"
    @on-cancel="handleClose"
  >
    <div class="title">添加</div>
    <div class="container">
      <Form ref="appendForm" label-position="top" :rules="rules" :model="listQuery">
        <FormItem prop="col" label="语言名称">
          <Input v-model="listQuery.col" clearable type="text" />
        </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="handleClose">取消</Button>
      <Button type="primary" :loading="loading" @click="handleSubmit('appendForm')">确定</Button>
    </div>
  </Modal>
</template>

<script>

import { appendCol } from '@/api/translate/translate'

export default {
  name: 'AppendModal',
  props: {
    showModal: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      listQuery: {
        col: ''
      },
      rules: {
        col: [{
          required: true,
          message: '请填写添加的语言名称'
        }]
      }
    }
  },
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate(async(valid) => {
        if (valid) {
          try {
            this.loading = true
            const { rtnMessage } = await appendCol(this.listQuery)
            this.$Message.success({
              content: rtnMessage,
              duration: 5,
              background: true
            })
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

<style scoped>
.title {
  font-size: 24px;
  font-weight: bold;
}

.container {
  margin: 10px 35px;
}
</style>
