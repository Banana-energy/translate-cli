<template>
  <div class="table-card">
    <Card dis-hover>
      <div v-if="$slots.header" class="search-header">
        <div class="title">
          {{ headerTitle || '筛选查询' }}
        </div>
        <div class="right">
          <slot name="header" />
        </div>
      </div>
      <div v-if="$slots.search" class="search-form">
        <slot name="search" />
      </div>
      <div class="list-header">
        <div class="list-header-title">
          <span class="text">{{ listTitle || '数据列表' }}</span>
          <span v-if="total" class="text">{{ total }}</span>
          <slot name="button" />
        </div>
        <div class="list-header-slot">
          <slot name="list-header" />
        </div>
      </div>
      <div class="list">
        <slot name="list" />
      </div>
    </Card>
  </div>
</template>
<script>
export default {
  props: {
    headerTitle: {
      type: String,
      default: ''
    },
    listTitle: {
      type: String,
      default: ''
    },
    total: {
      type: Number,
      default: null
    }
  }
}
</script>

<style lang="scss" scoped>
@import "./src/assets/styles/media_query";
.table-card {
  margin: 24px;

  .search-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .title {
      font-size: 24px;
      font-weight: bold;
    }
  }

  .search-form {
    margin: 10px 0;
  }

  .list-header {
    .list-header-title{
      margin-bottom: 10px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .text {
        flex-grow: 1;
        font-size: 24px;
        font-weight: bold;
      }
      @include in-middle-device {
        flex-direction: column;
        align-items: flex-start;
      }
    }
  }

  ::v-deep .ivu-scroll-container {
    max-height: 300px !important;
    height: initial !important;
  }
}
</style>
