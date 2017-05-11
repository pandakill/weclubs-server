package com.weclubs.util;

import com.weclubs.model.request.WCRequestModel;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 请求参数的处理类，提取请求参数里面的各种参数
 *
 * Created by fangzanpan on 2017/2/5.
 */
public class WCRequestParamsUtil {

//    {
//        id : 1234567890
//        client : {
//            caller : android_offical	// 用户APP的来源
//            version : v2.1	// 用户APP的版本号
//            date : 1222 // 用户手机端的时间戳
//            ex : {
//                sc : 720,1280	// 宽*高
//                dv : xiaomi_os	// 用户固件
//                uid : 122fff	// 用户唯一id
//                sf : pp	// 渠道
//                os : android	// 操作系统
//            }
//        }
//        encrypt : “md5”
//        sign : fafafafafafafa
//        data : {
//            user_id : 12
//        }
//    }

    public static String getRequestId (HashMap<String, Object> params) {
        if (null != params.get("id")) {
            try {
                return (String) params.get("id");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    public static String getRequestId (WCRequestModel requestModel) {
        if (null != requestModel.getId() && !"".equals(requestModel.getId())) {
            return requestModel.getId();
        }

        return null;
    }

    public static String getEncrypt(HashMap<String, Object> params) {
        try {
            if (null != params.get("encrypt")) {
                return (String) params.get("encrypt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getEncrypt(WCRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getEncrypt())) {
                    return requestModel.getEncrypt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static HashMap<String, Object> getRequestParams(HashMap<String, Object> params) {
        if (null != params.get("data")) {
            try {
                return (HashMap<String, Object>) params.get("data");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static <T> T getRequestParams(WCRequestModel requestModel, Class<T> targetClass) {
        try {
            if (null != requestModel.getData()) {
                return (T) requestModel.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientVersion(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("version");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClientVersion(WCRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getVersion())) {
                return requestModel.getClient().getVersion();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientCaller(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("caller");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClientCaller(WCRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getCaller())) {
                return requestModel.getClient().getCaller();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientDate(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("date");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getClientDate(WCRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getDate())) {
                return Long.parseLong(requestModel.getClient().getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static String getSign(HashMap<String, Object> params) {
        try {
            return (String) params.get("sign");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSign(WCRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getSign())) {
                return requestModel.getSign();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static long getUserId(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) params.get("data");
            long userId = 0;
            if (dataInfo.get("user_id") instanceof String) {
                userId = Long.parseLong((String) dataInfo.get("user_id"));
            } else if (dataInfo.get("user_id") instanceof Integer) {
                userId = Long.parseLong(String.valueOf(dataInfo.get("user_id")));
            } else if (dataInfo.get("user_id") instanceof Long) {
                userId = (Long) dataInfo.get("user_id");
            }
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getUserId(WCRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                long userId = 0;
                if (!data.containsKey("user_id")) {
                    return -1;
                }
                if (data.get("user_id") instanceof String) {
                    userId = Long.parseLong((String) data.get("user_id"));
                } else if (data.get("user_id") instanceof Integer) {
                    userId = Long.parseLong(String.valueOf(data.get("user_id")));
                } else if (data.get("user_id") instanceof Long) {
                    userId = (Long) data.get("user_id");
                }
                return userId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static String getToken(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) params.get("data");
            if (!params.containsKey("token")) {
                return null;
            }
            return (String) dataInfo.get("token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(WCRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                return (String) data.get("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 从请求参数中获取页码，如果没有上传页码或者上传的格式不符合要求，则会默认为page_no=1
     *
     * @param requestModel  请求参数
     * @return  页码
     */
    public static int getPageNo(WCRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                if (data == null || data.size() == 0) {
                    return 1;
                } else if (data.get("page_no") instanceof Integer) {
                    return (Integer) data.get("page_no");
                } else if (data.get("page_no") instanceof String) {
                    return Integer.parseInt((String) data.get("page_no"));
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 获取一页的大小，默认为 {@link Constants}{@code ONE_PAGE_SIZE}
     *
     * @param requestModel  请求参数
     * @return  一页的大小
     */
    public static int getPageSize(WCRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                if (data == null || data.size() == 0) {
                    return Constants.ONE_PAGE_SIZE;
                } else if (data.get("size") instanceof Integer) {
                    return (Integer) data.get("size");
                } else if (data.get("size") instanceof String) {
                    return Integer.parseInt((String) data.get("size"));
                } else {
                    return Constants.ONE_PAGE_SIZE;
                }
            } else {
                return Constants.ONE_PAGE_SIZE;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Constants.ONE_PAGE_SIZE;
        }
    }
}
