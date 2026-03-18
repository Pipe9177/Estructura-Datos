public class Pruebas {
    public static void main(String[] args) {
        Procesamiento procesador = new Procesamiento();

        // 1. Obtenemos los datos filtrados
        int datos[] = procesador.Procesar();

        // 2. PASAMOS esos datos a la matriz (Solución al error)
        int matriz[][] = procesador.Bahias(datos);

        // 3. Pasamos la matriz al manifiesto
        Modelo[] suministros = procesador.Vuelo(matriz);

        // --- IMPRESIÓN DEL RESULTADO ---
        System.out.println("\nMANIFIESTO DE VUELO (RESULTADO FINAL) --");
        for (int i = 0; i < suministros.length; i++) {
            System.out.print((i + 1) + ". ");
            if (suministros[i] != null) {
                System.out.println("\nSuministro [ id: " + suministros[i].id +
                        ", nivelEnergia: " + suministros[i].nivelEnergia +
                        ", prioridad: " + suministros[i].prioridad + "]");
            } else {
                System.out.println("[ null ]");
            }
        }
    }
}
