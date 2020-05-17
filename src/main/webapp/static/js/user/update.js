let vm = new Vue({
    el: '.main-content',
    data(){
        return{
            params:{
            },
            roles:{},
            path:'',
            isShow:false,
            active:false,
            name: '全部',
            setting:{
                data:{
                    key:{
                        title: 'title',
                    },
                    simpleData: {
                        enable:true ,//设置节点数组使用一维数组格式
                        pIdKey:'parentId'  //设置父id属性名
                    }
                },
                callback:{
                    onClick:this.onClick
                },
                view:{/*显示处理回调设置*/
                    fontCss: this.fontCss
                }
            },
        }
    },
    methods: {

        doUpdate(){
           axios({
               url:'manager/user/doUpdate',
               data:this.params,
               method:'post'
           }).then( resp=>{
               if(resp.data.success) {
                   let frameIndex = parent.layer.getFrameIndex(window.name);
                   parent.layer.close(frameIndex);
                   parent.layer.msg(resp.data.msg);
               }else{//失败: 显示提示
                   layer.msg(resp.data.msg);
               }
           }).catch(error=>{
               layer.msg(error.message)
           })
        },
        selectRole(){
            axios({
                url:'manager/user/selectRole',
                method:'post',
            })  .then( resp =>{
                this.roles = resp.data.obj;
            }).catch( eooro =>{

            })
        },
        showTree(flag){
            this.isShow = flag;
            this.flag = !this.flag
        },
        onClick(event, treeId, treeNode) {
            // console.log(event);
            // console.log(treeId);
            this.params.companyName=treeNode.name;
            console.log(treeNode.id);
            this.params.companyId = treeNode.id;
            this.params.officeId = treeNode.id;
            if (treeNode.id!=0) {
                this.params.officeId = treeNode.id
            }

        },
        initTree() {
            axios({
                url:'manager/Work/office',
                method:'post',
            }).then( resp =>{
                this.nodes = resp.data.obj;
                //动态生成一个根节点
                this.nodes[this.nodes.length]={id:0,name:'全部机构',open:true};
                let zTree = $.fn.zTree.init($('#pullDownTreethree'), this.setting, this.nodes);
            } ).catch( error =>{
                console.log(error.message)
            } )

        },
        search(){
            let zTreeObj = $.fn.zTree.getZTreeObj("pullDownTreeone");
            let nodesbyParamFuzzy = zTreeObj.getNodesByParamFuzzy("name", this.name, null);
            let treeNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
            for (let i = 0; i < treeNodes.length; i++) {
                treeNodes[i].hightLight=false;
                zTreeObj.updateNode(treeNodes[i]);
            }
            //将查找到的节点高亮属性设置为true
            for (let i in nodesbyParamFuzzy) {
                nodesbyParamFuzzy[i].hightLight=true;//设置高亮属性
                zTreeObj.updateNode(nodesbyParamFuzzy[i])//更新树对象上的节点信息，会自动触发显示回调
            }
        },
        fontCss(treeId,treeNode) {
            /*根据高亮标记设置高亮红色*/
            return treeNode.hightLight?{color:'red'}:{color:'black'};
        },

        createRid(e,params){
            this.params.roleId=params.selected;
        }
    },
    created() {

        this.selectRole();
        this.params=parent.layer.obj;
        this.params.password=null
        console.log(this.params);
    }
    , mounted(){
        this.initTree();
        $('#user-role-update').chosen({width:'100%'});//初始化wasteType的chosen
        $("#user-role-update").on("change",this.createRid);
    },
    updated(){
        $("#user-role-update").trigger("chosen:updated");


    }
})
