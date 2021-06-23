let liketotal = document.getElementById('num_likes');
let likes = Number(liketotal.innerText);
const btnDislike = document.getElementById('btn_Dislike')
const btnLike = document.getElementById('btn_Like')
var myVar = document.getElementById("myVar").value;

btnDislike.addEventListener('click', ()=>{
    likes -= 1;
    //liketotal.innerHTML = likes; // Tarea 7
    send_value(likes); // Tarea 8
})

btnLike.addEventListener('click', ()=>{
    likes += 1;
    //liketotal.innerHTML = likes; // Tarea 7
    send_value(likes); // Tarea 8
})

// realizamos la modificación de los likes usando el API
function send_value(value) {
    $.ajax({
        'url': myVar, // el end-point
        'type': 'PUT',
        'dataType': 'json', // Content-Type de vuelta
        'data': JSON.stringify({
            'likes': value
        }), // el api espera un string json
        'success': function (data) { // call-back, se ejecuta con el response
            //console.log(data + " success send");
            liketotal.innerHTML = likes;
        },
        error: function (result) {
            alert(JSON.stringify(result))
            //console.log(result.responseJSON + " error send");
        }
    })
}