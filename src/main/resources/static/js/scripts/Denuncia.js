const url = "/v1/denuncias";

function ajaxRequest(type, endpoint, data = null) {
    return $.ajax({
        type,
        url: endpoint,
        data: data ? JSON.stringify(data) : null,
        dataType: "json",
        contentType: data ? "application/json" : undefined,
        cache: false,
        timeout: 600000,
    });
}

function save(bandera) {
    const id = $("#guardar").data("id");
    const registro = {
        id,
        titulo: $("#titulo").val(),
        descripcion: $("#descripcion").val(),
        ubicacion: $("#ubicacion").val(),
        estado: $("#estado").val(),
        ciudadano: $("#ciudadano").val(),
        telefono_ciudadano: $("#telefono_ciudadano").val(),
        fechaInicio: $("#fechaInicio").val(),
    };

    const type = bandera === 1 ? "POST" : "PUT";
    const endpoint = bandera === 1 ? url : `${url}/${id}`;

    ajaxRequest(type, endpoint, registro)
        .done((data) => {
            if (data.ok) {
                $("#modal-update").modal("hide");
                getTabla();
                $("#error-message").addClass("d-none");
                Swal.fire({
                    icon: 'success',
                    title: `Se ha ${bandera === 1 ? 'guardado' : 'actualizado'} el registro`,
                    showConfirmButton: false,
                    timer: 1500
                });
                clear();
            } else {
                showError(data.message);
            }
        }).fail(function (jqXHR) {
           let errorMessage = jqXHR.responseJSON && jqXHR.responseJSON.message ? jqXHR.responseJSON.message : "Error inesperado. Código: " + jqXHR.status;
           showError(errorMessage);
        });
}

function showError(message) {
    $("#error-message").text(message).removeClass("d-none");
}

function deleteFila(id) {
    ajaxRequest("DELETE", `${url}/${id}`)
        .done((data) => {
            if (data.ok) {
                Swal.fire({
                    icon: 'success',
                    title: 'Se ha eliminado el registro',
                    showConfirmButton: false,
                    timer: 1500
                });
                getTabla();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.message,
                    confirmButtonText: 'Aceptar'
                });
            }
        })
        .fail(handleError);
}

function getTabla() {
    ajaxRequest("GET", url)
        .done((data) => {
            const t = $("#tablaRegistros").DataTable();
            t.clear().draw(false);

            if (data.ok) {
                $.each(data.body, (index, registro) => {
                    const botonera = `
                        <button type="button" class="btn btn-warning btn-xs editar">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button type="button" class="btn btn-danger btn-xs eliminar">
                            <i class="fas fa-trash"></i>
                        </button>
                    `;
                    const newRow = [
                        botonera,
                        registro.id,
                        registro.titulo,
                        registro.descripcion,
                        registro.ubicacion,
                        registro.estado,
                        registro.ciudadano,
                        registro.telefono_ciudadano,
                        registro.fechaInicio
                    ];
                    t.row.add(newRow).draw(false);
                });
            }
        })
        .fail(handleError);
}

function getFila(fila) {
    const id = fila.data()[1];
    ajaxRequest("GET", `${url}/${id}`)
        .done((data) => {
            if (data.ok) {
                const registro = data.body;
                $("#titulo").val(registro.titulo);
                $("#descripcion").val(registro.descripcion);
                $("#ubicacion").val(registro.ubicacion);
                $("#estado").val(registro.estado);
                $("#ciudadano").val(registro.ciudadano);
                $("#telefono_ciudadano").val(registro.telefono_ciudadano);
                $("#fechaInicio").val(registro.fechaInicio);
                $("#guardar").data("id", id);
                $("#modal-title").text("Editar Denuncia");
                $("#modal-update").modal("show");
            }
        })
        .fail(handleError);
}

function clear() {
    $("#titulo, #descripcion, #ubicacion, #estado, #ciudadano, #telefono_ciudadano, #fechaInicio").val("");
    $("#guardar").data("id", 0);
    $("#modal-title").text("Nueva Denuncia");
}

function handleError(jqXHR) {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: jqXHR.responseJSON ? jqXHR.responseJSON.message : `Error inesperado. Código: ${jqXHR.status}`,
        confirmButtonText: 'Aceptar'
    });
}

$(document).ready(() => {
    const t = $("#tablaRegistros").DataTable({
        columnDefs: [{ targets: 1, visible: false }],
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        responsive: true,
    });

    $("#guardar").on("click", () => {
        const bandera = $("#guardar").data("id") === 0 ? 1 : 2;
        save(bandera);
    });

    $("#nuevo").on("click", () => {
        clear();
        $("#modal-update").modal("show");
    });

    $("#tablaRegistros tbody").on("click", "button.editar", function () {
        const fila = t.row($(this).parents("tr"));
        getFila(fila);
    });

    $("#tablaRegistros tbody").on("click", "button.eliminar", function () {
        const fila = t.row($(this).parents("tr"));
        const id = fila.data()[1];
        Swal.fire({
            title: '¿Está seguro de eliminar esta denuncia?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) deleteFila(id);
        });
    });

    getTabla();
});

