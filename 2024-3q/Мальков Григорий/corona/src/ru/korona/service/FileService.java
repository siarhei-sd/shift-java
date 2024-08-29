package ru.korona.service;

public interface FileService {

    /**
     * Обрабатывает переданные аргументы файлов на старте программы.
     * Предполагается что аргументы переданные на вход связаны с файлами
     * согласно API.
     *
     * @param args - program arguments из консоли или конфигурации запуска
     */
    void processFiles(String[] args);
}
