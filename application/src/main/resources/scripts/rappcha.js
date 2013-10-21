Envjs.scriptTypes["text/javascript"] = true;

if (server) {
    console.log(server);
    console.log("Running on server " + server.name);
    console.log("Running on server with class name " + server.className);
    //server.ready();
    //window.location = "http://localhost:8080/blog/prod.html";
} else {
    console.log("Running on the client");
}