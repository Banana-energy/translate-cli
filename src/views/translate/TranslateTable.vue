<template>
  <div>
    <table-box>
      <template v-slot:header>
        <Button type="primary" icon="ios-search-outline" @click="handleSearch">
          搜索
        </Button>
      </template>
      <template v-slot:search>
        <Form ref="search-form" label-position="left" :model="listQuery" :label-width="55" inline>
          <FormItem label="关键字">
            <Input
              v-model="listQuery.words"
              clearable
              type="text"
              onkeypress="if(event.keyCode === 13) return false"
              placeholder="请输入关键字"
              @keyup.enter.native="handleSearch"
            />
          </FormItem>
        </Form>
      </template>
      <template slot="button">
        <div class="button-group">
          <Button icon="ios-add" @click="update({})">添加单词</Button>
          <Button icon="ios-add-circle-outline" style="margin-left: 10px;" @click="append">添加语言</Button>
          <Button
            :loading="exportLoading"
            style="margin-left: 10px;"
            icon="ios-cloud-download-outline"
            @click="exportExcel"
          >
            导出Excel
          </Button>
        </div>
      </template>
      <template v-slot:list>
        <div ref="translateTable">
          <Table :loading="loading" :columns="columns" :data="list" border highlight-row>
            <template slot="hashKey" slot-scope="{ row }">
              {{ row.hashKey }}
            </template>
            <template slot="action" slot-scope="{ row }">
              <Button type="text" size="small" @click="update(row)">编辑</Button>
              <Poptip
                confirm
                title="确认删除这条记录吗？"
                placement="bottom"
                @on-ok="handleRemove(row)"
                @on-cancel="cancel"
              >
                <Button type="text" size="small">删除</Button>
              </Poptip>
            </template>
          </Table>
          <div class="list-page">
            <Page
              :total="total"
              :current.sync="listQuery.page"
              size="small"
              show-total
              show-elevator
              @on-change="getList(false)"
            />
          </div>
        </div>
      </template>
    </table-box>
    <EditModal :row="rowData" :show-modal.sync="showModal" @refurbish="$emit('refresh')" @refresh="getList" />
    <AppendModal :show-modal.sync="appendModal" @refresh="getCols" />
  </div>
</template>

<script>
import { deleteWords, exportToExcel, getColumns, getTranslateList } from '@/api/translate/translate'
import EditModal from '@/views/translate/EditModal'
import AppendModal from '@/views/translate/AppendModal'

export default {
  name: 'TranslateTable',
  components: {
    EditModal,
    AppendModal
  },
  props: {
    name: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      list: [],
      columns: [],
      listQuery: {
        page: 1,
        words: '',
        name: ''
      },
      loading: false,
      total: 0,
      exportLoading: false,
      showModal: false,
      appendModal: false,
      rowData: {}
    }
  },
  watch: {
    name(val) {
      this.listQuery.name = val
      this.listQuery.page = 1
      this.getList()
    }
  },
  mounted() {
    this.getCols()
    this.getList(false)
  },
  methods: {
    async getCols() {
      const { data } = await getColumns()
      data.map((val) => {
        if (val === 'hash_key') {
          this.columns.push({
            title: val,
            key: val,
            slot: 'hashKey',
            align: 'center'
          })
        } else {
          this.columns.push({
            title: val,
            key: val,
            align: 'center'
          })
        }
      })
      this.columns.push({
        title: '操作',
        slot: 'action',
        fixed: 'right',
        className: 'list-action',
        align: 'center'
      })
    },
    async getList(scroll = true) {
      this.loading = true
      try {
        const { table, total } = await getTranslateList(this.listQuery)
        this.list = table
        this.total = total
      } finally {
        this.loading = false
      }
      if (scroll) {
        const el = this.$refs.translateTable
        el.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' })
      }
    },
    update(row = {}) {
      this.rowData = row
      this.showModal = true
    },
    async handleRemove(row) {
      const { rtnMessage } = await deleteWords(row)
      this.$Message.success({
        content: rtnMessage,
        duration: 5,
        background: true
      })
      await this.getList()
    },
    cancel() {
    },
    append() {
      this.appendModal = true
    },
    handleSearch() {
      this.listQuery.page = 1
      this.listQuery.name = ''
      this.getList()
    },
    async exportExcel() {
      this.exportLoading = true
      try {
        await exportToExcel(this.listQuery)
      } finally {
        this.exportLoading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.list-page {
  display: flex;
  justify-content: flex-end;
  margin: 20px 0 0 0;
}

::v-deep .list-action {
  .ivu-table-cell-slot {
    display: flex;
    justify-content: center;
    align-items: center;

    .ivu-btn-text {
      color: #2d8cf0;

      &:hover {
        background: none;
      }

      &:focus {
        box-shadow: none
      }

      span {
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
}

::v-deep .ivu-poptip-popper {
  left: 23px !important;
}

::v-deep .ivu-poptip-confirm .ivu-poptip-popper {
  max-width: 180px;
}

::v-deep .ivu-poptip-body {
  display: flex;
}

</style>
