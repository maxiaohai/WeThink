<template>
  <li>
    <div class="block" v-show="!!model.content">
      <div class="title-row">
        <span class="username">
          {{ model.username }}
        </span>
        <span class="time">
          {{ model.createTime | dateFilter }}
        </span>
      </div>
      <div class="content-row">
        {{ model.content }}
      </div>
      <div class="op-row">
        <el-button @click="reply" size="small" style="border: none">
          reply
        </el-button>
      </div>
      <el-divider />
    </div>
    <ul>
      <item
        class="item"
        v-for="model in model.children"
        :model="model"
        :key="model.title"
        v-bind="$attrs"
        v-on="$listeners"
      >
      </item>
    </ul>
  </li>
</template>
<script>
import moment from "moment";

export default {
  name: "Item",
  props: {
    model: Object,
  },
  filters: {
    dateFilter(date) {
      if (date) return moment(date).format("YYYY-MM-DD HH:mm:SS");
    },
  },
  data: function () {
    return {};
  },
  computed: {},
  methods: {
    reply() {
      this.$listeners.preply({ ...this.model, children: null });
    },
  },
};
</script>
<style scoped>
ul,
li {
  list-style: none;
}
.block {
  width: 80%;
}
.title-row {
  line-height: 20px;
}
.title-row .username {
  font-style: oblique;
}
.title-row .time {
  float: right;
  margin-right: 40px;
  font-size: 14px;
  color: #817f7f;
}

.content-row {
  font-size: 20px;
  margin-left: 20px;
  margin-top: 5px;
}
.op-row {
  float: right;
  line-height: 20px;
}
</style>
