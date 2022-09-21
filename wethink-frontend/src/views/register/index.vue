<template>
  <div class="register-container">
    <el-form
      ref="regForm"
      :model="regForm"
      :rules="registerRules"
      class="register-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">Register</h3>
      </div>

      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="regForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          ref="email"
          v-model="regForm.email"
          placeholder="Email"
          name="email"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="regForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          autocomplete="on"
        />
      </el-form-item>
      <el-form-item prop="repeatPwd">
        <el-input
          :key="passwordType"
          ref="repeatPwd"
          v-model="regForm.repeatPwd"
          :type="passwordType"
          placeholder="Repeat Password"
          name="repeatPwd"
          tabindex="2"
          autocomplete="on"
        />
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleRegister"
        >Submit</el-button
      >
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Register",
  data() {
    const validateUsername = (rule, value, callback) => {
      const reg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,20}$/;
      if (!value || value.length < 5 || value.length > 20 || !reg.test(value)) {
        callback(
          new Error("Length between 5 and 20, only digits and charactors")
        );
      } else {
        callback();
      }
    };
    const validateEmail = (rule, value, callback) => {
      const reg =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (!value || !reg.test(value)) {
        callback(new Error("Malformed email"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      let upper = false,
        lower = false,
        digit = false,
        sp = false;

      for (let i = 0; i < value.length; i++) {
        if (value[i] >= "a" && value[i] <= "z") upper = true;
        else if (value[i] >= "A" && value[i] <= "Z") lower = true;
        else if (value[i] >= "0" && value[i] <= "9") digit = true;
        else sp = true;
      }

      if (
        !value ||
        value.length < 8 ||
        value.length > 20 ||
        !(upper && lower && digit && sp)
      ) {
        callback(
          new Error(
            "Length between 8 and 20, at least one uppercase, one lower case and one specal charactor"
          )
        );
      } else {
        callback();
      }
    };
    const validateRepeatPwd = (rule, value, callback) => {
      if (this.regForm.password != value) {
        callback(new Error("Password is different from above"));
      } else {
        callback();
      }
    };
    return {
      regForm: {
        username: "",
        email: "",
        password: "",
        repeatPwd: "",
      },
      registerRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        email: [{ required: true, trigger: "blur", validator: validateEmail }],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
        repeatPwd: [
          {
            required: true,
            trigger: "blur",
            validator: validateRepeatPwd,
          },
        ],
      },
      passwordType: "password",
      loading: false,
    };
  },
  watch: {},
  created() {},
  mounted() {
    if (this.regForm.username === "") {
      this.$refs.username.focus();
    } else if (this.regForm.email === "") {
      this.$refs.email.focus();
    } else if (this.regForm.password === "") {
      this.$refs.password.focus();
    } else if (this.regForm.repeatPwd === "") {
      this.$refs.repeatPwd.focus();
    }
  },
  destroyed() {},
  methods: {
    handleRegister() {
      this.$refs.regForm.validate((valid) => {
        if (valid) {
          const form = {
            username: this.regForm.username,
            email: this.regForm.email,
            password: this.regForm.password,
          };
          axios.post("/user/register", form).then(({data}) => {
            if (data.code === 200) {
              this.$router.push("/login");
            } else {
              this.$message.error(data.msg || "Register failed");
            }
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.register-container {
  min-height: 100%;
  width: 100%;
  overflow: hidden;
}
.register-container .register-form {
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.register-container .title-container {
  position: relative;
}
.register-container .title-container .title {
  font-size: 26px;
  margin: 0px auto 40px auto;
  text-align: center;
  font-weight: bold;
}
</style>
