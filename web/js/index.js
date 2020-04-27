var wEditor;
var joditEditor;
var tip_id;
var domain = "https://openreading.natapp4.cc/";

/**
 * 检查登录状态，判断是否需要登录
 */
function checkIsNeedLogin() {
    httpRequest("/admin/loginStatusCheck", {}, function (data) {

    }, function (data) {
        if (data.status == 'success') {
            $("#center-div-container").load("../pages/CenterTipActionList.html", function () {
                $("#div-add-new-tip").css("background-color", "#e0e0eb");
            });//加载中视图
            $("#right-div-container").load("../pages/RightAddNewTip.html", function () {
                loadwangEditor();
            });//加载右视图
        } else {
            $("#right-div-container").load("../pages/RightLogin.html");
        }
    }, function (data) {
    });
}

/**
 * 登录
 */
function login() {
    var param = {email: $("#email").val(), password: $("#password").val()};
    httpRequest("/admin/login", param, function (data) {
        $("#center-div-container").load("../pages/CenterTipActionList.html");//
        $("#right-div-container").children().remove();//移除登录框
        $("#right-div-container").load("../pages/RightAddNewTip.html", function () {
            $("#div-add-new-tip").css("background-color", "#e0e0eb");
            loadEditor();
        });

    }, function (data) {

    },function (data) {
    });
}

//切换中视图
function switchCenterContainer(that) {
    var id = $(that).attr("id");

    var centerNode = "";
    var centerPage = "";
    var rightPage = "";
    centerNode = "div-add-new-tip";
    centerPage = "../pages/CenterTipActionList.html";
    rightPage = "../pages/RightAddNewTip.html";

    $("#left-div-container").children(".sub-div-container").css("background-color", "");
    $("#center-div-container").children().remove();
    $("#right-div-container").children().remove();
    $("#center-div-container").load(centerPage, function () {//加载中视图
        $("#" + centerNode).css("background-color", "#e0e0eb");//中视图第一个节点修改颜色
    });
    $("#right-div-container").load(rightPage, function () {
        if (id == "div-left-tip") {
            loadEditor();
        }
    });//加载右视图
}

//点击中视图，切换右视图
function switchRightContainer(that) {
    var id = $(that).attr("id");

    var targetFile;
    if (id == "div-add-new-admin") {
        targetFile = "../pages/RightAddNewAdmin.html";
    } else if (id == "div-admin-list") {
        targetFile = "../pages/RightAdminList.html";
    } else if (id == "div-add-new-user") {
        targetFile = "../pages/RightAddNewUser.html";
    } else if (id == "div-user-list") {
        targetFile = "../pages/RightUserList.html";
    } else if (id == "div-add-new-tip") {
        targetFile = "../pages/RightAddNewTip.html";
    } else if (id == "div-tip-list") {
        targetFile = "../pages/RightTipList.html";
    } else if (id == "div-add-new-tag") {
        targetFile = "../pages/RightAddNewTag.html";
    } else if (id == "div-tag-list") {
        targetFile = "../pages/RightTagList.html";
    } else if (id == "div-tip-update") {
        targetFile = "../pages/RightTipUpdate.html";
    }

    $("#right-div-container").children().remove();
    $("#center-div-container").children().css("background-color", "");
    $("#right-div-container").load(targetFile, function () {
        $("#" + id).css("background-color", "#e0e0eb");
        if (id == "div-add-new-tip") {
            loadEditor();
        } else if (id == "div-tip-update") {
            loadEditor();
        }
    });

}

//加载富文本编辑器
function loadEditor() {
    loadwangEditor();
}

//加载Jodit编辑器
function loadJodit() {
    joditEditor = new Jodit('#editor', {
        uploader: {
            url: "/file/upload",
            insertImageAsBase64URI: false,
            imagesExtensions: [
                "jpg",
                "png",
                "jpeg",
                "gif"
            ],
            filesVariableName: 'files',
            withCredentials: false,
            pathVariableName: "path",
            format: "json",
            method: "POST",
            prepareData: function (formdata) {
                file = formdata.getAll("files[0]")[0];
                formdata.append("aboutMePic", file);
                return formdata;
            },
            isSuccess: function (e) {
                console.log(e);
                return e.data;
            },
            getMessage: function (e) {
                return void 0 !== e.data.messages && Array.isArray(e.data.messages) ? e.data.messages.join("") : ""
            },
            process: function (resp) {
                var ss = this;
                console.log(resp);
                var arrfile = [];
                arrfile.unshift(resp.data);
                console.log(arrfile.length + "" + arrfile[0]);
                return {
                    files: arrfile,
                    path: '',
                    baseurl: '',
                    error: resp.msg,
                    msg: resp.msg
                };
            },
            error: function (e) {
                this.jodit.events.fire("errorMessage", e.message, "error", 4e3)
            },
            defaultHandlerSuccess: function (resp) {

            },
            defaultHandlerError: function (e) {
                this.jodit.events.fire("errorMessage", e.message)
            },
            contentType: function (e) {
                return (void 0 === this.jodit.ownerWindow.FormData || "string" == typeof e) &&
                    "application/x-www-form-urlencoded; charset=UTF-8"
            }
        },
        filebrowser: {
            ajax: {
                url: 'http://localhost:8181/index-test.php'
            }
        }
    });

}

//加载wangEditor
function loadwangEditor() {
    var E = window.wangEditor;
    wEditor = new E('#editor');
    wEditor.customConfig.height = 600;
    wEditor.customConfig.uploadImgServer = '/file/upload';
    wEditor.customConfig.uploadImgHooks = {
        before: function (xhr, editor, files) {

        },
        success: function (xhr, editor, result) {


        },
        fail: function (xhr, editor, result) {

        },
        error: function (xhr, editor) {

        },
        timeout: function (xhr, editor) {

        },
        customInsert: function (insertImg, result, editor) {
            for (var i = 0; i < result.length; i++) {
                insertImg(domain + "image/download/" + result[i]);
            }

        }
    };
    wEditor.create();
}

//加载贴士
function loadTip() {
    var tipId = $('#tipIdInput').val();
    if (tipId != "") {
        httpRequest("/tip/query", {"tip_id": tipId}, function (data) {
            var map = data[0];
            tip_id = map.id;
            $('#title').val(map.title);
            $('#summary').val(map.summary);
            wEditor.txt.html(map.content);
        }, function (data) {

        }, function (data) {
        });
    }

}

//更新贴士
function updateTip() {
    var id = $("#tipIdInput").val();
    var tilte = $("#title").val();
    var summary = $("#summary").val();
    var content = wEditor.txt.html();
    if (id != "" && tilte != "" && summary != "" && content != "<p><br></p>") {
        var param = {"tip_id": id, "title": tilte, "summary": summary, "content": content};
        httpRequest("/tip/update", param, function (data) {
            $('#id').val("");
            $('#title').val("");
            $('#summary').val("");
            wEditor.txt.html("<p><br></p>");
        }, function (data) {
            if (data.status == "too_long") {
                alert("修改失败，标题/简介/内容太长");
            } else if (data.status == "too_short") {
                alert("修改失败，标题/简介/内容为空");
            } else if (data.status == "fail") {
                alert("修改失败");
            } else if (data.status == "success") {
                alert("修改成功");
            } else if (data.status == "illegal") {
                alert("修改失败，含有非法字符");
            }
        }, function (data) {
        });
    } else {
    }
}

//创建贴士
function addTip() {
    var tilte = $("#title").val();
    var summary = $("#summary").val();
    var content = wEditor.txt.html();
    if (tilte != "" && summary != "" && content != "<p><br></p>") {
        var param = {"title": tilte, "summary": summary, "content": content};
        httpRequest("/tip/add", param, function (data) {
            var id = data[0].id;
            alert("创建成功，ID为" + id);
            $("#title").val("");
            $("#summary").val("");
            wEditor.txt.html("<p><br></p>");
        },function (data) {
            if (data.status = "too_long") {
                alert("创建失败，标题/简介/内容太长");
            } else if (data.status == "too_short") {
                alert("创建失败，标题/简介/内容为空");
            } else if (data.status == "fail") {
                alert("创建失败");
            }
        },function (data) {
        });
    } else {
    }
}

/**
 * 网络请求
 * @param method
 * @param url
 * @param param
 * @param successArr
 * @param successDic
 * @param error
 */
function httpRequest(url, param, successArr, successDic, error) {
    $.ajax({
        type: 'post',
        data: param,
        url: url,
        success: function (result) {
            console.log(result);
            var data = JSON.parse(result);
            if (result.startsWith("{", 0)) {
                successDic(data);
            } else if (result.startsWith("[", 0)) {
                successArr(data);
            } else {
                error(result);
            }
        },
        error: function (e) {
        }
    })
}