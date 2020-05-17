let vm = new Vue({
    el: '.main-content',
    data: {
        detail:{}
    },
    methods: {

    },
    created() {
        this.detail=parent.layer.obj
    }
})
