var finnes;

$("#registrer").click(() => {
    jQuery.ajaxSetup({async:false});
    let fornavn = document.getElementById('fornavn').value;
    let etternavn = document.getElementById('etternavn').value;
    let adresse = document.getElementById('adresse').value;
    let postnr = document.getElementById('postnr').value;
    let telefonnr = document.getElementById('telefonnr').value;
    let epost = document.getElementById('epost').value;
    let pakkevolum = document.getElementById('volum').value;
    let vekt = document.getElementById('vekt').value;


    if(!fornavn.match("[a-zæøåA-ZÆØÅ]{0,50}")) {
        $("#feil").text("Feil i fornavn");
        return;
    }

    if(!etternavn.match("[a-zæøåA-ZÆØÅ]{0,50}")) {
        $("#feil").text("Feil i etternavn");
        return;
    }

    if(!postnr.match("^[0-9]{4}$")) {
        $("#feil").text("Feil i postnr");
        return;
    }


    sjekkPostNr(postnr);
    if(finnes != true) {
        $("#feil").text("Postnummeret finnes ikke.");
        return;
    }




    let data = {
        "fornavn": fornavn,
        "etternavn": etternavn,
        "adresse": adresse,
        "postnr": postnr,
        "telefonnr": telefonnr,
        "epost": epost,
        "pakkevolum": pakkevolum,
        "vekt": vekt
    }
    const url = "/sendData";


    $.post(url, data, () => {
        window.location.href = "/";
    }).fail((jqXHR) => {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
});


function sjekkPostNr(postNr) {
    const url = "/sjekkPostnr?postnr=" + postNr;
    $.get(url, (response) => {
        finnes = response;
    });
}

