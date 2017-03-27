package com.weclubs.model.request;

import java.io.Serializable;

/**
 * 请求参数基本类型
 *
 * Created by fangzanpan on 2017/2/6.
 */
public class WCRequestModel implements Serializable {

    private String id;
    private ClientBean client;
    private String encrypt;
    private String sign;
    private Object data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WCRequestModel{" +
                "id='" + id + '\'' +
                ", client=" + client +
                ", encrypt='" + encrypt + '\'' +
                ", sign='" + sign + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ClientBean implements Serializable {

        private String caller;
        private String version;
        private String date;
        private ExBean ex;

        public String getCaller() {
            return caller;
        }

        public void setCaller(String caller) {
            this.caller = caller;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ExBean getEx() {
            return ex;
        }

        public void setEx(ExBean ex) {
            this.ex = ex;
        }

        @Override
        public String toString() {
            return "ClientBean{" +
                    "caller='" + caller + '\'' +
                    ", version='" + version + '\'' +
                    ", date='" + date + '\'' +
                    ", ex=" + ex +
                    '}';
        }

        public static class ExBean implements Serializable {

            private String sc;
            private String dv;
            private String uid;
            private String sf;
            private String os;

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getDv() {
                return dv;
            }

            public void setDv(String dv) {
                this.dv = dv;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getSf() {
                return sf;
            }

            public void setSf(String sf) {
                this.sf = sf;
            }

            public String getOs() {
                return os;
            }

            public void setOs(String os) {
                this.os = os;
            }

            @Override
            public String toString() {
                return "ExBean{" +
                        "sc='" + sc + '\'' +
                        ", dv='" + dv + '\'' +
                        ", uid='" + uid + '\'' +
                        ", sf='" + sf + '\'' +
                        ", os='" + os + '\'' +
                        '}';
            }
        }
    }
}
