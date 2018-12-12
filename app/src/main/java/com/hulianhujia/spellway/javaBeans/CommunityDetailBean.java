package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/13.
 */

public class CommunityDetailBean {
    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"cmnt_id":"1","username":"17781481226","title":"eikd","content":"哄你YY","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","zan":"0","comment":"0","listorder":"0","create_time":"1500451884","update_time":"","count":"","status":"1","judge":[{"jg_id":"1","cateid":"1","username":"huni","jgd_id":"1","content":"这里的景色真好看","jg_time":"1498727185","status":"1","nickname":"","picture":""},{"jg_id":"20","cateid":"1","username":"17781481226","jgd_id":"1","content":"命","jg_time":"1500630659","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"21","cateid":"1","username":"17781481226","jgd_id":"1","content":"我日","jg_time":"1500630797","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"}],"judgeCount":"11","nickname":"Tom"}
     */
    private int code;
    private String msg;
    /**
     * cmnt_id : 1
     * username : 17781481226
     * title : eikd
     * content : 哄你YY
     * picture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * zan : 0
     * comment : 0
     * listorder : 0
     * create_time : 1500451884
     * update_time :
     * count :
     * status : 1
     * judge : [{"jg_id":"1","cateid":"1","username":"huni","jgd_id":"1","content":"这里的景色真好看","jg_time":"1498727185","status":"1","nickname":"","picture":""},{"jg_id":"20","cateid":"1","username":"17781481226","jgd_id":"1","content":"命","jg_time":"1500630659","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"21","cateid":"1","username":"17781481226","jgd_id":"1","content":"我日","jg_time":"1500630797","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"}]
     * judgeCount : 11
     * nickname : Tom
     */

    private ReturnArrBean returnArr;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReturnArrBean getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(ReturnArrBean returnArr) {
        this.returnArr = returnArr;
    }

    public static class ReturnArrBean {
        private String cmnt_id;
        private String username;
        private String title;
        private String content;
        private String picture;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String update_time;
        private String count;
        private String usernick;
        private String userpicture;
        private String status;
        private String judgeCount;
        private String nickname;
        private String where;

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
        }

        public String getUsernick() {
            return usernick;
        }

        public void setUsernick(String usernick) {
            this.usernick = usernick;
        }

        public String getUserpicture() {
            return userpicture;
        }

        public void setUserpicture(String userpicture) {
            this.userpicture = userpicture;
        }

        /**
         * jg_id : 1
         * cateid : 1
         * username : huni
         * jgd_id : 1
         * content : 这里的景色真好看
         * jg_time : 1498727185
         * status : 1
         * nickname :
         * picture :
         */

        private List<JudgeBean> judge;

        public String getCmnt_id() {
            return cmnt_id;
        }

        public void setCmnt_id(String cmnt_id) {
            this.cmnt_id = cmnt_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getJudgeCount() {
            return judgeCount;
        }

        public void setJudgeCount(String judgeCount) {
            this.judgeCount = judgeCount;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<JudgeBean> getJudge() {
            return judge;
        }

        public void setJudge(List<JudgeBean> judge) {
            this.judge = judge;
        }

        public static class JudgeBean {
            private String jg_id;
            private String cateid;
            private String username;
            private String jgd_id;
            private String content;
            private String jg_time;
            private String status;
            private String nickname;
            private String userPicture;

            public String getUserpicture() {
                return userPicture;
            }

            public void setUserpicture(String userpicture) {
                this.userPicture = userpicture;
            }

            public String getJg_id() {
                return jg_id;
            }

            public void setJg_id(String jg_id) {
                this.jg_id = jg_id;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getJgd_id() {
                return jgd_id;
            }

            public void setJgd_id(String jgd_id) {
                this.jgd_id = jgd_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getJg_time() {
                return jg_time;
            }

            public void setJg_time(String jg_time) {
                this.jg_time = jg_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }


        }
    }
}
