let vm = new Vue({
    el: '.main-content',
    data(){
        return{
            ueditorConfig:{
                // ueditor.config.js->VueUeditorWrap 覆盖为"/static/UEditor/"  -> ueditorConfig覆盖为 /day63/ueditor_demo/
                UEDITOR_HOME_URL:"static/ueditor/",  //前端默认起始路径
                serverUrl: 'ueditor' ,    //服务器统一请求接口
                maximumWords:500000
            },
            office:{},
            wasteTypes:{},
            wastes:{},
            updateWastes:[],
            wasteVal:'0',//用于初始化显示waste
            changeWastes:false
        }
    },
    methods: {
        toSelect:function () {

            layer.open({
                type:2,
                content:'manager/area/toSelect',
                title:'选择父级区域',
                area:['100%','100%'],
                end:() => {//select页关闭后的回调操作  需要将页面传递过来的参数获取，赋值给当前节点
                    console.log(layer.obj);
                    if(layer.obj!=undefined&&layer.obj.id!=this.office.parentId){
                        //设置新的parent_ids 和parent_id 和parentName
                        this.office.areaName = layer.obj.name;
                        this.office.parentId = layer.obj.id;
                        // 以广州更新到 重庆为例   ：  重庆的parentIds+重庆的id+','
                        this.office.parentIds = layer.obj.parentIds+layer.obj.id+',';
                    }
                }
            })
        },
        selectByOid:function (id) {
            axios({
                url:`manager/office/selectWasteByOid/${id}`
            }).then(response=>{
                this.updateWastes=response.data.obj;//获取已绑定的所有wastes
                console.log(response.data.obj);
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        addWastes:function (e,params) {
            //如果传入的waste的id与updateWastes的id一致则是已存在
            for (let i = 0; i < this.updateWastes.length; i++) {
                if(this.updateWastes[i].id==params.selected){
                    return;
                }
            }
            //不是默认的value为0  且 不存在则添加
            for (let i = 0; i <this.wastes.length; i++) {
                if(this.wastes[i].id==params.selected){
                    // console.log(this.updateWastes);
                    this.updateWastes.push(this.wastes[i])
                    this.changeWastes=true;
                }
            }

        },
        removeWaste:function (id) {
            //遍历updateWastes找到下标位置
            // 通过splice(index,num)  index:需要开始删除的下标  num : 删除个数
            for (let i = 0; i < this.updateWastes.length; i++) {
                if(this.updateWastes[i].id==id){
                    this.updateWastes.splice(i,1);
                    this.changeWastes=true;
                }
            }
        },
        createWasteTypes:function () {//动态初始化select列表
            axios({
                url:'manager/office/selectWasteType'
            }).then(response=>{
                this.wasteTypes=response.data.obj;

            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        createWastes:function (e,params) {
            //e:事件对象   params:选中选项封装的对象  {selected:value}
            axios({
                url:`manager/office/selectWaste/${params.selected}`
            }).then(response=>{

                this.wastes=response.data.obj;//获取指定id的wasteType的所有waste
                let wasteTypeCode ;
                for (let i = 0; i < this.wasteTypes.length; i++) {
                    if(this.wasteTypes[i].id==params.selected){
                        wasteTypeCode = this.wasteTypes[i].code;
                        break;
                    }

                }
                //动态赋值 wasteTypeCode
                for (let i = 0; i <this.wastes.length; i++) {
                    this.wastes[i].wasteTypeCode=wasteTypeCode;
                }


            }).catch(error=>{
                layer.msg(error.message)
            })

        },
        doUpdate(){

            if(!this.changeWastes){
                this.office.wastes={};
            }else{
                this.office.wastes=this.updateWastes;
            }

            axios({
                url:'manager/office/doUpdate',
                method:'post',
                data:this.office
            }).then(response=>{
                console.log(this);
                //成功:关闭窗口  在父窗口显示提示
                if(response.data.success) {
                    let frameIndex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameIndex);
                    parent.layer.msg(response.data.msg);
                }else{//失败: 显示提示
                    layer.msg(response.data.msg);
                }

            }).catch(error=>{
                layer.msg(error.message)
            })
        }
    },
    components:{
        VueUeditorWrap
    },
    created() {
        this.office = parent.layer.obj;
        this.createWasteTypes();
        this.selectByOid(this.office.id);
    },
    mounted(){
        $('#chosen-select').chosen({width:'100%'});
        $('#wasteType').chosen({width:'100%'});//初始化wasteType的chosen
        $("#wasteType").on("change",this.createWastes);
        $('#waste').chosen({width:'100%'});
        $("#waste").on("change",this.addWastes);
    },
    updated(){
        $("#wasteType").trigger("chosen:updated");
        $("#waste").trigger("chosen:updated");

    }
})
