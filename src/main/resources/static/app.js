var stompClient = null;

function conversationHide() {

    $("#conversation").hide();
    $( "#sala" ).focus();
}

function setConnected(connected) {

    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    $("#name").prop("disabled", !connected);
    $("#sala").prop("disabled", !connected);


    if (connected) {

        $("#conversation").show();
        $("#name").prop("disabled", !connected);
        $("#send").prop("disabled", !connected);
        $("#sala").prop("disabled", connected);

    }
    else {

        $("#conversation").hide();

        $("#send").prop("disabled", !connected);
        $("#name").prop("disabled", !connected);
        $("#sala").prop("disabled", connected);

        $("#sala").val(null);
        $("#name").val(null);

        conversationHide();
    }


    $("#greetings").html("");
}

function connect() {

    if ($.trim($("#sala").val().length) < 3) {
        alert('nome invalido');
        return;
    }

    var socket = new SockJS('/likwi-chat');
    console.log("conectando na URL:" + socket.url);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.send("/chat/" + $.trim($("#sala").val().toUpperCase()), {}, JSON.stringify({'name': 'Nova Conexao'}));

        stompClient.subscribe('/broadcast/' + $.trim($("#sala").val().toUpperCase()), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });


        $("#name").focus();

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

    stompClient.send("/chat/" + $.trim($("#sala").val().toUpperCase()), {}, JSON.stringify({'name': $("#name").val()}));

    $("#name").val(null);
    $("#name").focus();
}

function showGreeting(message) {
    $("#greetings").prepend("<tr> <td>"+ moment(Date.now()).format("HH:mm:ss") +"</td>  <td>" + message + "</td></tr>");
    // $("#greetings").append("<tr><td>" + message + "</td></tr>");
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
