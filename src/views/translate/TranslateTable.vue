<template>
  <table-box>
    <template v-slot:header>
      <Button type="primary" icon="ios-search-outline" @click="handleSearch">
        搜索
      </Button>
    </template>
    <template v-slot:search>
      <Form ref="search-form" label-position="left" :model="listQuery" :label-width="55" inline>
        <FormItem label="关键字">
          <Input v-model="listQuery.words" clearable class="" type="text" placeholder="请输入关键字" />
        </FormItem>
      </Form>
    </template>
    <template slot="button">
      <Row :gutter="16">
        <Button icon="md-add">添加</Button>
        <Button icon="primary">导出Excel</Button>
      </Row>
    </template>
    <template v-slot:list>
      <Table :loading="loading" :columns="columns" :data="list" border highlight-row>
        <template slot="hashKey" slot-scope="{ row }">
          {{ row.hashKey }}
        </template>
        <template slot="action" slot-scope="{ row }">
          <Button type="text" size="small" @click="update(row)">编辑</Button>
          <Button type="text" size="small" @click="remove(row)">删除</Button>
        </template>
      </Table>
      <div class="list-page">
        <Page
          :total="total"
          :current.sync="listQuery.page"
          size="small"
          c
          show-total
          show-elevator
          @on-change="getList"
        />
      </div>
    </template>
  </table-box>
</template>

<script>
import { getColumns, getTranslateList } from '@/api/translate/translate'

export default {
  name: 'TranslateTable',
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
        words: ''
      },
      loading: true,
      total: 0
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
    this.getList()
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
    async getList() {
      this.loading = true
      const { table, total } = await getTranslateList(this.listQuery)
      this.list = table
      this.total = total
      this.loading = false
    },
    update(row) {
      console.log(row)
    },
    remove(row) {

    },
    handleSearch() {
      this.listQuery.page = 1
      this.listQuery.name = ''
      this.getList()
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
    }
  }
}
</style>
