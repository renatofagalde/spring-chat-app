var stompClient = null;

function setConnected(connected) {

    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    $("#name").prop("disabled", !connected);



    if (connected) {

        $("#conversation").show();
        console.log("Valor de connectd, dentro do if-true : " + connected);

        $("#name").prop("disabled", !connected);
        $("#send").prop("disabled", !connected);

    }
    else {

        console.log("Valor de connectd, dentro do if-false : " + connected);
        $("#conversation").hide();
        $("#send").prop("disabled", !connected);
        $("#name").prop("disabled", !connected);
    }


    $("#greetings").html("");
}

function connect() {

    if ( $.trim( $("#sala").val().length ) <3 ) {
        alert('nome invalido');
        return;
    }

    var socket = new SockJS('/likwi-chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.send("/chat/"+$.trim($("#sala").val()), {}, JSON.stringify({'name': 'Nova Conexao'}));

        stompClient.subscribe('/broadcast/'+$.trim($("#sala").val()), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
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

function sendName() {

    stompClient.send("/chat/"+$.trim($("#sala").val()), {}, JSON.stringify({'name': $("#name").val()}));

    $("#name").val(null);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
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
        sendName();
    });
});