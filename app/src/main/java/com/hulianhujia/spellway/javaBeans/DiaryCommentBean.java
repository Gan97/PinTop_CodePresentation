package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/14.
 */

public class DiaryCommentBean  {


    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"news_id":"6","cateid":"","username":"18875016690","title":"你妈批","content":"你.哦7亿新坡送心热破7只","picture":"http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a15cb.jpg,http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a1cbe.jpg,","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1501642709","update_time":"","count":"","status":"1","judge":[{"jg_id":"43","cateid":"2","username":"17781481226","jgd_id":"6","content":"恩","jg_time":"1501745296","status":"1","nickname":"Tom","userPicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","user_id":"6"},{"jg_id":"44","cateid":"2","username":"17781481226","jgd_id":"6","content":"啊","jg_time":"1501745304","status":"1","nickname":"Tom","userPicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","user_id":"6"}],"judgeCount":"2","collected":0,"collectionCount":"0","nickname":"","userPicture":"http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/598199917587e.jpg","user_id":"19"}
     */

    private int code;
    private String msg;
    /**
     * news_id : 6
     * cateid :
     * username : 18875016690
     * title : 你妈批
     * content : 你.哦7亿新坡送心热破7只
     * picture : http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a15cb.jpg,http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a1cbe.jpg,
     * where : 成都市
     * zan : 0
     * comment : 0
     * listorder : 0
     * create_time : 1501642709
     * update_time :
     * count :
     * status : 1
     * judge : [{"jg_id":"43","cateid":"2","username":"17781481226","jgd_id":"6","content":"恩","jg_time":"1501745296","status":"1","nickname":"Tom","userPicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","user_id":"6"},{"jg_id":"44","cateid":"2","username":"17781481226","jgd_id":"6","content":"啊","jg_time":"1501745304","status":"1","nickname":"Tom","userPicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","user_id":"6"}]
     * judgeCount : 2
     * collected : 0
     * collectionCount : 0
     * nickname :
     * userPicture : http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/598199917587e.jpg
     * user_id : 19
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
        private String news_id;
        private String cateid;
        private String username;
        private String title;
        private String content;
        private String picture;
        private String where;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String update_time;
        private String count;
        private String status;
        private String judgeCount;
        private int collected;
        private String collectionCount;
        private String nickname;
        private String userPicture;
        private String user_id;
        private String url;
        private int is_zan;

        public int getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(int is_zan) {
            this.is_zan = is_zan;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * jg_id : 43
         * cateid : 2
         * username : 17781481226
         * jgd_id : 6
         * content : 恩
         * jg_time : 1501745296
         * status : 1
         * nickname : Tom
         * userPicture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
         * user_id : 6
         */

        private List<JudgeBean> judge;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
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

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
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

        public int getCollected() {
            return collected;
        }

        public void setCollected(int collected) {
            this.collected = collected;
        }

        public String getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(String collectionCount) {
            this.collectionCount = collectionCount;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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
            private String user_id;

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

            public String getUserPicture() {
                return userPicture;
            }

            public void setUserPicture(String userPicture) {
                this.userPicture = userPicture;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
