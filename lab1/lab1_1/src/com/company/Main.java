package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Задание 1.\n");

        double[][] matrix =  {{15,10,0,-6,17},
                              {3,14,8,9,2},
                              {1,5,14,20,-3},
                              {7,19,10,2,0}};


        outputMatrix(matrix, "Матрица доходов:");
        System.out.printf("Z = %.3f\n", waldMethod(matrix));
        System.out.printf("Z = %.3f\n", savageMethod(matrix));
        System.out.printf("Z = %.3f\n", gurvisMethod(matrix));

        System.out.println("Задание 2.\n");

        double[][] probabilityMatrix =  {{100,150,200,250,300},{0.2,0.25,0.3,0.15,0.1}};

        double[][] answerMat = getResultMatrix(probabilityMatrix);

        outputMatrix(probabilityMatrix, "Таблица спроса и вероятности:");
        outputMatrix(answerMat, "Матрица решений:");

        int index = bayesLaplaceMethod(answerMat, probabilityMatrix[1]);
        if (index != -1) {
            System.out.printf("оптимальное кол-во заказов: %.0f\n", probabilityMatrix[0][index]);
        }


    }

    private static void outputMatrix(double[][] matrix, String message) {
        System.out.println(message);
        for (double[] row:matrix) {
            for (double element: row) {
                System.out.printf("%.3f\t", element);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printVector(double[] vector) {
        System.out.print("[");
        for (double element : vector) {
            System.out.printf("%.3f, ",element);
        }
        System.out.print("]");
        System.out.println();
    }
//----------------Задание 1-------------------------------
    private static double waldMethod(double[][] matrix) {
        System.out.println("Минимаксный метод:");
        double[] tmp = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++)
            tmp[i] = Arrays.stream(matrix[i]).min().getAsDouble();

        printVector(tmp);

        return Arrays.stream(tmp).max().getAsDouble();
    }
//------------------------Метод Сэвиджа (S)---------------------------------------------
    private static double savageMethod(double[][] matrix) {
        System.out.println("Метод Сэвиджа (S):");

        double[][] deltaK = new double[matrix.length][matrix[0].length];
        double[] k = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                deltaK[i][j] = getMaxColumn(matrix, j) - matrix[i][j];

        for(int i = 0; i < k.length; i++)
            k[i] = Arrays.stream(deltaK[i]).max().getAsDouble();

        printVector(k);

        return Arrays.stream(k).min().getAsDouble();
    }

    private static double getMaxColumn(double[][] matrix, int index) {
        double[] column = new double[matrix.length];

        for (int i = 0; i < column.length; i++) {
            column[i] = matrix[i][index];
        }

        return Arrays.stream(column).max().getAsDouble();
    }
//------------------------Метод Гурвица (HW)-------------------------------------------

    private static double gurvisMethod(double[][] matrix) {
        System.out.println("Метод гурвица Гурвица (HW):");

        double[] tmp = new double[matrix.length];
        double c = 0.87;

        for (int i = 0; i < tmp.length; i++)
            tmp[i] = c * Arrays.stream(matrix[i]).min().getAsDouble() + (1 - c) * Arrays.stream(matrix[i]).max().getAsDouble();

        printVector(tmp);

        return Arrays.stream(tmp).max().getAsDouble();
    }
///-----------------------------Задание 2-------------------------------------------
    private static double[][] getResultMatrix(double[][] array){
        double[][] answerMat = new double[array[0].length][array[0].length];

        for (int i = 0; i < answerMat.length; i++) {
            for (int j = 0; j < answerMat[0].length; j++) {
                if (i == j || i < j) {
                    answerMat[i][j] = 49 * array[0][i] - 25 * array[0][i];
                } else {
                    answerMat[i][j] = 49 * array[0][j] - 25 * array[0][i] + 15 * (array[0][i] - array[0][j]);
                }
            }
        }

        return answerMat;
    }

    private static int bayesLaplaceMethod(double[][] resultMatrix, double[] probabilityVector) {
        System.out.println("Метод Байеса-Лапласа:");
        double[] e = new double[probabilityVector.length];

        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++){
                e[i] += resultMatrix[i][j] * probabilityVector[j];
            }
        }

        printVector(e);
        double max = Arrays.stream(e).max().getAsDouble();
        System.out.printf("Z = %.3f\n", max);

        int index = -1;
        for (int i = 0; i < e.length; i++) {
            if(e[i] == max) {
                index = i;
                break;
            }
        }

        return index;
    }


}
