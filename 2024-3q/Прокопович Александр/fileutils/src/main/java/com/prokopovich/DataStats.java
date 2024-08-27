package com.prokopovich;

class DataStats {
    private int intCount = 0;
    private double intSum = 0;
    private int intMin = Integer.MAX_VALUE;
    private int intMax = Integer.MIN_VALUE;

    private int floatCount = 0;
    private double floatSum = 0;
    private float floatMin = Float.MAX_VALUE;
    private float floatMax = Float.MIN_VALUE;

    private int stringCount = 0;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = Integer.MIN_VALUE;

    public void updateIntStats(String data) {
        int value = Integer.parseInt(data);
        intCount++;
        intSum += value;
        if (value < intMin) {
            intMin = value;
        }
        if (value > intMax) {
            intMax = value;
        }
    }

    public void updateFloatStats(String data) {
        float value = Float.parseFloat(data);
        floatCount++;
        floatSum += value;
        if (value < floatMin) {
            floatMin = value;
        }
        if (value > floatMax) {
            floatMax = value;
        }
    }

    public void updateStringStats(String data) {
        int length = data.length();
        stringCount++;
        if (length < minLength) {
            minLength = length;
        }
        if (length > maxLength) {
            maxLength = length;
        }
    }

    public String getShortStats() {
        return "Краткая статистика:\n" +
                "Целые числа: " + intCount + "\n" +
                "Вещественные числа: " + floatCount + "\n" +
                "Строки: " + stringCount;
    }

    public String getFullStats() {
        StringBuilder sb = new StringBuilder("Полная статистика:\n");
        if (intCount > 0) {
            double avg = intSum / intCount;
            sb.append("Целые числа: ").append(intCount)
                    .append(", Минимум: ").append(intMin)
                    .append(", Максимум: ").append(intMax)
                    .append(", Сумма: ").append(intSum)
                    .append(", Среднее: ").append(avg).append("\n");
        } else {
            sb.append("Целые числа: 0\n");
        }
        if (floatCount > 0) {
            double avg = floatSum / floatCount;
            sb.append("Вещественные числа: ").append(floatCount)
                    .append(", Минимум: ").append(floatMin)
                    .append(", Максимум: ").append(floatMax)
                    .append(", Сумма: ").append(floatSum)
                    .append(", Среднее: ").append(avg).append("\n");
        } else {
            sb.append("Вещественные числа: 0\n");
        }
        if (stringCount > 0) {
            sb.append("Строки: ").append(stringCount)
                    .append(", Минимальная длина: ").append(minLength)
                    .append(", Максимальная длина: ").append(maxLength);
        } else {
            sb.append("Строки: 0");
        }
        return sb.toString();
    }

    public int getIntCount() {
        return intCount;
    }

    public double getIntSum() {
        return intSum;
    }

    public int getIntMin() {
        return intMin;
    }

    public int getIntMax() {
        return intMax;
    }

    public int getFloatCount() {
        return floatCount;
    }

    public double getFloatSum() {
        return floatSum;
    }

    public float getFloatMin() {
        return floatMin;
    }

    public float getFloatMax() {
        return floatMax;
    }

    public int getStringCount() {
        return stringCount;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }
}