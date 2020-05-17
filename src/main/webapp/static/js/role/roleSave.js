let vm = new Vue({
    el: '.main-content',
    data(){
        return {
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'//根据node节点中的parentId属性来作为pId属性值
                    }
                },
                check: {
                    enable: true,
                    chkboxType: {'Y': 'ps', 'N': 'ps'}//设置选中和取消选中的时候对父子树节点影响  Y:控制选中关联     N：控制取消选中关联
                },
                callback: {
                    onCheck: this.onClick
                },

            },

            nodes: [],  //资源树节点数组
            officeNodes: [],
            treeObj: {},
            role: {
                offices: null,
                resources: null,

            },
            params: {
                officeName: '',
                roleName: '',
                    id: '',
                dataScope: '',
                roleId:[],
                resourceId:[],
            },
            reAll: {},
            reCheck: {},
            officeByRid: {},
            reCheckFlag: -1,
            officeByRidFlag: -1
        }
    },
    methods: {
        onClick(event, treeId, treeNode) {
           if (treeId=="select-treetreeSelectResEdit"){
               this.reCheck=this.treeObj.getCheckedNodes()
           }else if (treeId=="select-treetreeSelectOfficeEdit"){
               this.officeByRid=this.officeTreeObj.getCheckedNodes()
           }


        },
        initTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/role/selectAll'
            }).then(response => {
                this.nodes = response.data.obj;//   this.setNodes(.....)
                this.nodes[this.nodes.length]={
                    "id": 0,
                    "name": "所有权限"
                }//动态设置根节点

                //根据当前树的全部节点中查找到需要设置选中的节点，选中处理  (显示已分配权限)
                this.selectByRid();


            }).catch(function (error) {
                layer.msg(error.message);
            })
        },
        selectCheck(){
            axios({
                url:`manager/role/selectResource/${this.params.id}`,
            }).then(resp=>{
                this.reCheck=resp.data.obj;
            }).catch(error=>{

            })
        },
        selectByRid(){
            for (let a in this.nodes) {
                this.reCheck.some((item,i)=>{
                    if (item.id==this.nodes[a].id){
                        this.nodes[a].checked=true;//设置选中权限
                        return true
                    }
                })
            }
            this.treeObj =   $.fn.zTree.init($("#select-treetreeSelectResEdit"),this.setting,this.nodes);
        },
        selectOffice(){
            axios({
                url:'manager/Work/office',
                method:'post',
            }).then( resp =>{
                this.officeNodes = resp.data.obj;
                //动态生成一个根节点
                this.officeNodes[this.officeNodes.length]={id:0,name:'全部机构',open:true};
                this.selectOfficeByRid()
                // let zTree = $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.officeNodes);
            } ).catch( error =>{
                console.log(error.message)
            } )
        },
        selectOfficeByRid(){
            axios({
                url:`manager/Work/selectOfficeByRid/${this.params.id}`,
                method:'post',
            }).then( resp =>{
                this.officeByRid = resp.data.obj;
                for (let a in this.officeNodes) {
                    this.officeByRid.some((item,i)=>{
                        if (item.id==this.officeNodes[a].id){
                            this.officeNodes[a].checked=true;//设置选中权限
                            return true
                        }
                    })
                }
                this.officeTreeObj =   $.fn.zTree.init($("#select-treetreeSelectOfficeEdit"),this.setting,this.officeNodes);
                $("#treeSelectOfficeEdit").css("display","inline-block");//显示隐藏的office树
            } ).catch( error =>{
                console.log(error.message)
            } )
        },
        changeDataScope:function (e,param) {//chosen  绑定的 change事件调用函数  自动传入e:事件   param:{selected:选项值}
            this.params.dataScope=param.selected;
            //判断   如果是从其他改为明细查询
            if(this.params.dataScope==='9'){
                //如果公司树未初始化，才进行初始化
                if(this.officeTreeObj===''){
                    this.selectOffice();
                }else{//是已经初始化过公司树，切换到了其他选项，又切换回来
                    $("#treeSelectOfficeEdit").css("display","inline-block");
                }
            }else {  //  改为非9的情况        隐藏公司树 不删除先
                $("#treeSelectOfficeEdit").css("display","none");
            }
        },
        doRoleUpdate(){

            if (this.reCheckFlag>0 &&this.reCheck.length>0 && this.officeByRidFlag>0 &&this.officeByRid.length>0){
                this.reCheck.splice(0,1);

                this.officeByRid.splice(0,1);
                for (let i = 0; i <this.reCheck.length ; i++) {
                    this.params.roleId.push(this.reCheck[i].id)
                }
                for (let i = 0; i <this.officeByRid.length ; i++) {
                    this.params.resourceId.push(this.officeByRid[i].id)
                }

            }else if (this.reCheckFlag>0&&this.reCheck.length>0){
                this.reCheck.splice(0,1);

                for (let i = 0; i <this.reCheck.length ; i++) {
                    this.params.roleId.push(this.reCheck[i].id)
                }

            } else if (this.officeByRidFlag>0&&this.officeByRid.length>0){
                this.officeByRid.splice(0,1);
                for (let i = 0; i <this.officeByRid.length ; i++) {
                    this.params.resourceId.push(this.officeByRid[i].id)
                }

            }
            axios({
                url:'manager/role/doRoleUpdate',
                data:this.params,
                method:'post'
            }).then(resp=>{
                if (resp.data.success){
                    let frameIndex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameIndex);
                    parent.layer.msg(resp.data.msg)
                }
                else {
                    layer.msg(resp.data.msg)
                }
            }).catch(error=>{
                console.log(error.message);
            })

        }
    },
    watch:{
        reCheck(){
            this.reCheckFlag+=1;
            console.log(this.reCheck);
        },
        officeByRid(){
            this.officeByRidFlag+=1;
        }
    },
    created() {
        this.params.officeName=parent.layer.officeName;
        this.params.roleName=parent.layer.name;
        this.params.id=parent.layer.rid;
        this.params.dataScope=parent.layer.dataScope;
        this.selectCheck();
        if(this.params.dataScope==='9'){
            this.selectOffice()
        }
    },
    mounted(){
        this.initTree();
        $("#chosenSelectEdit").chosen({width: "40%",search_contains: true});
        $("#chosenSelectEdit").on("change",this.changeDataScope);
    },

})
