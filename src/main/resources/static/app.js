var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    var socket = new SockJS('/dn-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function (res) {
            showMessage(JSON.parse(res.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/chat/save-get", {
        'page': 0,
        'size': 100,
        'sort': 'createdDate',
        'direction': 'asc'
    }, JSON.stringify({
        'me': $("input[name='me']").val(),
        'you': $("input[name='you']").val(),
        'message': $("textarea[name='message']").val()
    }));
}

function showMessage(res) {
    $("#messages").html("");
    $.each(res, function (i, v) {
        $("#messages").append("<tr>" +
            "<td>" + v.me + "</td>" +
            "<td>" + v.you + "</td>" +
            "<td>" + v.message + "</td>" +
            "</tr>");
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});

