package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Utilidad para hashear contraseñas con SHA-256
 * Proporciona seguridad básica para el almacenamiento de contraseñas
 */
public class HashUtil {

    /**
     * Hashea una contraseña usando SHA-256
     * @param contrasena Contraseña en texto plano
     * @return Hash hexadecimal de la contraseña
     */
    public static String hashPassword(String contrasena) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña: " + e.getMessage());
        }
    }

    /**
     * Verifica si una contraseña coincide con un hash
     * @param contrasena Contraseña en texto plano a verificar
     * @param hash Hash almacenado
     * @return true si la contraseña coincide con el hash
     */
    public static boolean verificarPassword(String contrasena, String hash) {
        String hashGenerado = hashPassword(contrasena);
        return hashGenerado.equals(hash);
    }

    /**
     * Convierte un array de bytes a hexadecimal
     * @param bytes Array de bytes
     * @return String hexadecimal
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
