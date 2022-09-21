<template>
  <div class="login-container">

    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">Login</h3>
      </div>
      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username or email"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          autocomplete="on"
          @blur="capsTooltip = false"
          @keyup.enter.native="handleLogin"
        />
      </el-form-item>
      <el-checkbox style="margin: 5px 0" v-model="loginForm.remember"
        >remember me</el-checkbox
      >

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 4px"
        @click.native.prevent="handleLogin"
        >
        Submit</el-button>
      <el-button
          type="danger"
          style="width: 100%; margin-left: 0"
          @click.native.prevent="gotoRegister"
      >
        Register
      </el-button>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
import { UsernameKey, UserIdKey, EmailKey } from "../../constants";
import Qs from 'querystring'

export default {
  name: "Login",
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!value) {
        callback(new Error("Please enter the correct username or email"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value.length < 8 || value.length > 20) {
        callback(
          new Error(
            "The password can not be less than 8 charactor or more than 20"
          )
        );
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
        remember: false,
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      passwordType: "password",
      capsTooltip: false,
      loading: false,
    };
  },
  watch: {},
  created() {},
  mounted() {
    if (this.loginForm.username === "") {
      this.$refs.username.focus();
    } else if (this.loginForm.password === "") {
      this.$refs.password.focus();
    }
  },
  destroyed() {},
  methods: {
    gotoRegister() {
      this.$router.push('/register');
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          const form = {
            username: this.loginForm.username,
            password: this.loginForm.password,
            "remember-me": this.loginForm.remember,
          };
          axios({
            method: 'post',
            url: '/login', // 路径
            data: {
              ...form
            },
            transformRequest: [function (data) {
             return Qs.stringify(data);
            }],
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          }).then(({data}) => {
            console.log({data})
              if (data && data.code === 200) {
                const userInfo = data.data.principal;
                console.log({userInfo})
                localStorage.setItem(UsernameKey, userInfo.username);
                localStorage.setItem(EmailKey, userInfo.email);
                localStorage.setItem(UserIdKey, userInfo.id);
                this.$router.push("/comment");
              } else {
                this.$message.error("Login failed");
              }
          }).catch((err) => {
            console.log({err});
            this.loading = false;
            this.$message.error(err.response.data.msg)
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style  scoped>
.login-container {
  min-height: 100%;
  width: 100%;
  overflow: hidden;
}

.login-container .login-form {
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.login-container .svg-container {
  padding: 6px 5px 6px 15px;
  color: #889aa4;
  vertical-align: middle;
  width: 30px;
  display: inline-block;
}

.login-container .title-container {
  position: relative;
}

.login-container .title-container .title {
  font-size: 26px;
  margin: 0px auto 40px auto;
  text-align: center;
  font-weight: bold;
}
</style>
