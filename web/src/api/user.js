
//import request from '@/utils/request.js'
import axios from "axios";
//提供调用申请验证码接口的函数
export const userApplyService = async(applyData) => {
    let result =await axios.post("/api/user/register/send-code",applyData)
    return result
}
//提供调用注册接口的函数
export const userRegisterService = async(registerData) => {
    let result =await axios.post("/api/user/register/verify-code",registerData)
    return result
}

//提供调用登录接口的函数
export const userLoginService = async(loginData) => {
    let result =await axios.post("/api/user/login",loginData)
    return result
}

