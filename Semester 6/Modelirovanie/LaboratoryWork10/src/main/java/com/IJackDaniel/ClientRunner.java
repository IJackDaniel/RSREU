package com.IJackDaniel;

public class ClientRunner {

    // Интенсивность входного потока
    static final double LAMBDA = 0.5;

    // Интенсивность обслуживания
    static final double MU = 1.0 / 1.5;

    // Количество заявок
    static final int TOTAL_REQUESTS = 100000;

    public static void main(String[] args) {


        double currentTime = 0.0;

        // Момент освобождения канала
        double serviceEndTime = 0.0;

        int served = 0;
        int rejected = 0;

        double busyTime = 0.0;

        for (int i = 0; i < TOTAL_REQUESTS; i++) {

            // Генерация времени между заявками
            double interArrival =
                    -Math.log(UniversalGenerator.rnd()) / LAMBDA;

            currentTime += interArrival;

            // Проверка занятости канала
            if (currentTime >= serviceEndTime) {

                // Заявка обслуживается
                served++;

                // Генерация времени обслуживания
                double serviceTime =
                        -Math.log(UniversalGenerator.rnd()) / MU;

                busyTime += serviceTime;

                serviceEndTime = currentTime + serviceTime;

            } else {

                // Отказ
                rejected++;
            }
        }

        double refusalProbability =
                (double) rejected / TOTAL_REQUESTS;

        double serviceProbability =
                (double) served / TOTAL_REQUESTS;

        double relativeThroughput =
                serviceProbability;

        double absoluteThroughput =
                LAMBDA * relativeThroughput;

        double utilization =
                busyTime / currentTime;

        System.out.println("Результат моделирования");

        System.out.println("Всего заявок: " + TOTAL_REQUESTS);

        System.out.println("Обслужено: " + served);

        System.out.println("Отказано: " + rejected);

        System.out.println("Вероятность отказа: "
                + refusalProbability);

        System.out.println("Вероятность обслуживания: "
                + serviceProbability);

        System.out.println("Относительная пропускная способность: "
                + relativeThroughput);

        System.out.println("Абсолютная пропускная способность: "
                + absoluteThroughput);

        System.out.println("Коэффициент загрузки системы: "
                + utilization);
    }
}