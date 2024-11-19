package org.example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Определяем аннотацию с целочисленным параметром
@Retention(RetentionPolicy.RUNTIME) // Указывает, что аннотация доступна во время выполнения
@interface Repeat {
    int value(); // параметр для указания количества повторений, имя элемента аннотации
}

// Класс с различными методами
class MyClass {

    // Публичный метод
    public void publicMethod() {
        System.out.println("Public method called");
    }

    // Защищенный метод с аннотацией
    @Repeat(3)
    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    // Приватный метод с аннотацией
    @Repeat(2)
    private void privateMethod() {
        System.out.println("Private method called");
    }

    // Метод для вызова аннотированных методов
    public void invokeAnnotatedMethods() {
        // Проходим по всем методам текущего класса
        for (var method : this.getClass().getDeclaredMethods()) {
            // Проверяем, есть ли у метода аннотация @Repeat
            if (method.isAnnotationPresent(Repeat.class)) {
                // Получаем аннотацию @Repeat, примененную к методу
                Repeat repeat = method.getAnnotation(Repeat.class);
                // Делаем метод доступным, даже если он приватный
                method.setAccessible(true);
                // Выполняем метод указанное количество раз
                for (int i = 0; i < repeat.value(); i++) {
                    try {
                        // Вызываем метод на текущем экземпляре класса
                        method.invoke(this);
                    } catch (Exception e) {
                        // Обрабатываем исключения и выводим информацию об ошибке
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

// Класс для запуска
public class annotation {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.invokeAnnotatedMethods(); // Вызываем аннотированные методы
    }
}
