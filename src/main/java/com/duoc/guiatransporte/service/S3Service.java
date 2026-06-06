package com.duoc.guiatransporte.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public String construirClave(String fecha, String transportista, String nombreArchivo) {
        return fecha + "/" + transportista + "/" + nombreArchivo;
    }

    public String subirDesdeEfs(String rutaEfs, String fecha, String transportista, String nombreArchivo) {
        String clave = construirClave(fecha, transportista, nombreArchivo);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(clave)
                        .build(),
                RequestBody.fromFile(Paths.get(rutaEfs))
        );

        return clave;
    }

    public byte[] descargarArchivo(String claveS3) {
        return s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(claveS3)
                        .build()
        ).asByteArray();
    }

    public String reemplazarDesdeEfs(String rutaEfs, String fecha, String transportista, String nombreArchivo) {
        return subirDesdeEfs(rutaEfs, fecha, transportista, nombreArchivo);
    }

    public void eliminarArchivo(String claveS3) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(claveS3)
                        .build()
        );
    }

    public List<String> listarPorFechaYTransportista(String fecha, String transportista) {
        String prefijo = fecha + "/" + transportista + "/";

        return s3Client.listObjectsV2(
                        ListObjectsV2Request.builder()
                                .bucket(bucketName)
                                .prefix(prefijo)
                                .build()
                ).contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}