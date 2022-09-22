package com.wethink.dto;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDto {
    @NotEmpty(message="用户名不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,20}$", message = "用户名为数字和字母组合,长度在5~20之间")
    private String username;

    @Email(message="邮箱格式不正确")
    private String email;

    @NotEmpty(message="密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}",
            message = "密码长度要在8~20之间且至少包含一个大写、一个小写、一个数字、一个特殊符号")
    private String password;

}
