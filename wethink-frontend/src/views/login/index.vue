<template>
  <div class="login">
    <link href="https://fonts.googleapis.com/css?family=Poppins:600&display=swap" rel="stylesheet"/>
    <img class="login-wave" src="../../png/wave.png"/>
    <div class="login-banner">
     <img class="login-team" src="../../svg/team.svg"/>
    </div>
    <div class="login-container">
      <div class="login-avatar">
        <img class="login-avatar-svg" src="../../svg/avatar.svg"/>
      </div>
      <div class="title-container">
          <h1 class="title">WELCOME</h1>
      </div>
      <div class="login-form">
        <div class="input-div user">
          <div class="user">
            <i class="fas fa-user"></i>
          </div>
          <div class="username">
            <h5>Username</h5>
            <input type="text" class="input">
          </div>
        </div>
        <div class="input-div pass">
          <div class="lock"> 
            <i class="fas fa-lock"></i>
          </div>
          <div class="password">
            <h5>Password</h5>
            <input type="password" class="input">
          </div>
        </div>
        <!-- <el-checkbox style="margin: 5px 0" v-model="loginForm.remember"
          >remember me</el-checkbox
        > -->
        <a href="#">Forgot Password ?</a>
        <input type="submit" class="login-submit" value="LOGIN"/>
      </div>

      <!-- <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        autocomplete="on"
        label-position="left"
      > -->
       
        <!-- <el-form-item prop="username">
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
        </el-form-item> -->
        <!-- <el-checkbox style="margin: 5px 0" v-model="loginForm.remember"
          >remember me</el-checkbox
        >
        <a href="#">Forgot Password ?</a>
        <el-button
          :loading="loading"
          type="primary"
          style="width: 100%; margin-bottom: 4px"
          @click.native.prevent="handleLogin"
          >
          LOGIN</el-button> -->
      <!-- </el-form> -->
    </div>
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

.login {
  display: flex;
}

.login .login-wave {
  position: fixed;
	bottom: 0;
	left: 0;
	height: 100%;
	z-index: -1;
}
.login .login-banner {
  margin-top: 12%;
  margin-left: 11%;
  width: 35%;
}

.login .login-container{
  font-family: 'Poppins', sans-serif;
  margin-top: 0%;
  min-height: 100%;
  width: 50%;
  margin: auto;
  overflow: hidden;
}

.login .login-container .login-avatar .login-avatar-svg{
  margin-top: 20%;
  margin-left: 42%;
  width: 15%;
}

.login .login-container .login-form {
  position: relative;
  width: 300px;
  max-width: 100%;
  margin: 0 auto;
  overflow: hidden;
}

.login .login-container .title-container .title {
  font-size: 2.9rem;
  text-align: center;
  margin: 15px 0;
	color: #333;
	text-transform: uppercase;
}
.login .login-container .input-div{
	position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 5px 0;
  border-bottom: 2px solid #d9d9d9;
}
.login .login-container .login-submit {
  display: block;
	width: 100%;
	height: 50px;
	border-radius: 25px;
	outline: none;
	border: none;
	background-image: linear-gradient(to right, #32be8f, #38d39f, #32be8f);
	background-size: 200%;
	font-size: 1.2rem;
	color: #fff;
	font-family: 'Poppins', sans-serif;
	text-transform: uppercase;
	margin: 1rem 0;
	cursor: pointer;
	transition: .5s;
}
.login .login-container .login-form .input-div > div > i{
	color: #d9d9d9;
	display: flex;
	justify-content: center;
	align-items: center;
}
.login .login-container .login-form .input-div > div > h5{
	position: absolute;
	left: 10px;
	top: 50%;
	transform: translateY(-50%);
	color: #999;
	font-size: 18px;
	transition: .3s;
}

a{
	display: block;
	text-align: right;
	text-decoration: none;
	color: #999;
	font-size: 0.9rem;
	transition: .3s;
}
.login .login-container .login-form .input-div > div > input{
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	border: none;
	outline: none;
	background: none;
	padding: 0.5rem 0.7rem;
	font-size: 1.2rem;
	color: #555;
	font-family: 'poppins', sans-serif;
}
</style>
