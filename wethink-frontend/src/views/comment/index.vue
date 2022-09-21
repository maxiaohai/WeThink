<template>
  <div>
    <div class="article">
      <div class="title">
      Advice to a Young Man
      </div>
      <div class="body">
           Remember, yong man, you have to work. Whether you handle a pick or a pen, a wheel-barrow or a set of books, you must work. If you look around, you will see the men who are the most able to live the rest of their days without work are the men who work the hardest. Don't be afraid of killing yourself with overwork. It is beyond your power to do that on the sunny side of thirty. They die sometimes, but it is because they quit work at six in the evening, and do not go home until two in the morning. It is the interval that kills, my son. The work gives you an appetite for your meals; it gives you a perfect and grateful appreciation of a holiday.
      </div>
    </div>

    <item class="comment" v-on:preply="preply"  :model="commentTreeList"></item>
    <div v-show="curUsername" class="my-center">
      <div class="username">
        {{ curUsername }} <p></p> {{curEmail}}
      </div>
      <el-button
        @click="logout"
        size="small"
        type="danger"
        style="margin-top: 6px"
        >Logout</el-button
      >
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      :title="`reply to ${replyUser}`"
    >
      <el-input
        type="textarea"
        v-model="replyContent.content"
        maxlength="200"
        show-word-limit
        :rows="2"
      >
      </el-input>
      <div style="text-align: right; margin-top: 10px">
        <el-button type="danger" @click="dialogVisible = false">
          cancel
        </el-button>
        <el-button type="primary" @click="confirm"> confirm </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Item from "./item";
import axios from "axios";
import { UsernameKey, UserIdKey, EmailKey } from "../../constants";

export default {
  name: "Comment",
  components: { Item },
  data() {
    return {
      curUsername: "",
      replyUser: "",
      curEmail: "",
      commentTreeList:[],
      dialogVisible: false,
      replyContent: this.defaultReplyContent(),
      search: { parentId: null },
    };
  },
  created() {
    this.curUsername = localStorage.getItem(UsernameKey);
    this.curEmail = localStorage.getItem(EmailKey);
    this.getReplyData();
  },
  mounted() {},
  methods: {
    getReplyData() {
      let url = "comments/findAll";
      if (this.search.parentId) {
        url += `?parentId=${this.search.parentId}`;
      }
      axios.get(url).then((data) => {
        console.log('comment list response: ', data.data.data)
        this.commentTreeList = data.data.data;
      });
    },
    defaultReplyContent() {
      return {
        content: "",
        userId: null,
        parentId: null,
        username: '',
      };
    },
    preply(data) {
      this.replyContent.parentId = data.id;
      this.replyUser = data.username;
      this.replyContent.userId = localStorage.getItem(UserIdKey);
      this.replyContent.username = localStorage.getItem(UsernameKey);
      if (!this.replyContent.username) return this.loginTip();
      this.dialogVisible = true;
    },
    loginTip() {
      this.$confirm("You need login to reply comment ", "Login tip", {
        confirmButtonText: "confirm",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(() => {
          this.$router.push("/login");
        })
        .catch(() => {});
    },
    validateCommnet() {
      if (this.replyContent.content.length < 3) {
        this.$message.error(
          "The length of comment content can not less than 3"
        );
        return false;
      }
      return true;
    },
    confirm() {
      if (!this.validateCommnet()) return;
      if (!localStorage.getItem(UsernameKey)) this.loginTip();
      else
        axios.post("/comments/save", this.replyContent).then(({data}) => {
          if (data.code === 200) {
            this.$message({ type: "success", message: data.msg });
            this.getReplyData();
            this.dialogVisible = false;
          } else {
            this.$message({ type: "error", message: data.msg || "failed" });
          }
        });
    },
    logout() {
      axios
        .post("/logout")
        .then(({data}) => {
          if (data.code === 200) {
            localStorage.setItem(UsernameKey, "");
            this.$router.go(0);
          } else {
            this.$message.error(data.msg || "logout failed");
          }
        })
        .catch(() => {
          this.$message.error("logout failed");
        });
    },
  },
};
</script>
<style scoped>
.comment {
  margin-left: 10%;
}
.my-center {
  position: fixed;
  top: 10px;
  right: 10px;
}
.my-center .username {
  padding: 7px;
  margin-right: 10px;
}
.article {
  margin-top: 20px;
  margin-bottom: 30px;
}
.article .title {
  text-align: center;
  font-size: 30px;
}
.article .body {
  margin-top: 15px;
  font-size: 26px;
  width: 80%;
  margin-left: 10%;
}
</style>