let vm = new Vue({
    el: '.main-content',
    data(){
        return{
            active:false,
            checkPassWord:'',
            sysUser:{
                username:'',
                password:'',
                realName:'',
                companyId:'',
                officeId:'',
                roleId:'',
                no:'',
                email:'',
                mobile:'',
                phone:'',
                remarks:''
            },
            params:{
                oid:'',
                realName:'',
                no:'',
                rid:''
            },
            pageInfo:{
                pageNum:1,
                pageSize:5
            },
            roles:{},
            path:'',
            isShow:false,
            name: '请选择机构',
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
        toDetail(id) {
            layer.obj=id;
            layer.open({
                type: 2,    //弹出  frame 窗口
                title: 'App详情',
                area: ['80%', '80%'],  //宽,高   比例是占据父窗口的比例
                content: 'manager/user/toDetail',
                end: function () {
                }
            })
        },
        doDelete(id){
            let _this = this;
            layer.confirm('是否删除该记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                axios({
                    url: 'manager/user/doDelete',
                    method: 'post',
                    data: {
                        id:id,
                        delFlag:"1"
                    }
                }).then(resp => {
                    if (resp.data.success) {
                        layer.msg(resp.data.msg, {icon: 1});
                        _this.selectPage(1,_this.pageInfo.pageSize);
                    }
                    else {
                        layer.msg(resp.data.msg, {icon: 1})
                    }
                }).catch(error => {
                    layer.msg(error.message)
                });

            });
        },

        doInsert(){
            if (this.checkPassWord==this.sysUser.password){
                this.sysUser.officeId = this.sysUser.companyId
              axios({
                  url:'manager/user/doInsert',
                  data:this.sysUser,
                  method:'post'
              }).then( resp=>{
                  if (resp.data.success){
                      layer.msg(resp.data.msg);
                      this.active=false;
                      this.sysUser={
                          username:'',
                          password:'',
                          realName:'',
                          companyId:'',
                          officeId:'',
                          roleId:'',
                          no:'',
                          email:'',
                          mobile:'',
                          phone:'',
                          remarks:''
                      }
                      this.name='请选择机构';
                      this.params={
                          oid:'',
                              realName:'',
                              no:'',
                              rid:''
                      },
                      this.selectPage(1,this.pageInfo.pageSize);
                  } else{
                      layer.msg(resp.data.msg);
                  }
              }).catch(error=>{
                  layer.msg(error.message)
              })
           }else{
               layer.msg("两次输入的密码不一致");
               this.checkPassWord=''
           }
        },
        activeChange(){
            this.active =!this.active
        },
        selectPage(pageNum,pageSize){
            axios({
                url: `manager/user/selectPage/${pageNum}/${pageSize}`,
                method: 'post',
                data:this.params
            }).then(resp => {
                this.pageInfo = resp.data.obj;
            }).catch(error => {
            });
        },
        selectAll(pageNum, pageSize) {
            this.params={
                oid:'',
                realName:'',
                no:'',
                rid:''
            };
            this.selectPage(pageNum,pageSize)

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
            this.name=treeNode.name;
            if (this.active==true){
                this.sysUser.companyId = treeNode.id;
            }
            this.params.oid = treeNode.id;
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
                let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes);
                let zTreet = $.fn.zTree.init($('#pullDownTreetwo'), this.setting, this.nodes);
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
        toUpdate(obj) {
            layer.obj=obj;
            layer.open({
                type:2,    //弹出  frame 窗口
                title:'更新用户',
                area:['90%','90%'],  //宽,高   比例是占据父窗口的比例
                content:'manager/user/toUpdate',
            })
        },
        createRid(e,params){
          this.params.rid=params.selected;
          if (this.active==true){
              this.sysUser.roleId = params.selected;
          }
        }
    },
    created() {
        this.selectPage(1,this.pageInfo.pageSize);
        this.selectRole()
    }
    , mounted(){
        this.initTree()
        $('#role-select').chosen({width:'80%'});//初始化wasteType的chosen
        $("#role-select").on("change",this.createRid);
        $('#role-choose').chosen({width:'80%'});//初始化wasteType的chosen
        $("#role-choose").on("change",this.createRid);
    },
    updated(){
        $("#role-choose").trigger("chosen:updated");


    }
})
