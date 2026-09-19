package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class LifeModel {

    private LocalDate birthDate;

    private final PropertyChangeSupport propertyChangeSupport;

    public LifeModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setBirthDate(LocalDate birthDate) {

        LocalDate oldDate = this.birthDate;

        this.birthDate = birthDate;

        propertyChangeSupport.firePropertyChange(
                "birthDate",
                oldDate,
                birthDate
        );

        propertyChangeSupport.firePropertyChange(
                "statistics",
                null,
                null
        );
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener
    ) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(
            PropertyChangeListener listener
    ) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void checkBirthDate() {

        if (birthDate == null) {
            throw new IllegalStateException(
                    "Дата рождения ещё не введена."
            );
        }
    }

    public long getDaysLived() {

        checkBirthDate();

        return ChronoUnit.DAYS.between(birthDate, LocalDate.now());
    }

    public Period getAge() {

        checkBirthDate();

        return Period.between(birthDate, LocalDate.now());
    }

    public double getSleepHours() {

        return getDaysLived()
                * LifeStatistics.SLEEP_HOURS_PER_DAY;
    }

    public double getSleepDays() {

        return getSleepHours() / 24.0;
    }

    public long getBlinks() {

        double result =
                getDaysLived()
                        * 24.0
                        * 60.0
                        * LifeStatistics.BLINKS_PER_MINUTE;

        return Math.round(result);
    }

    public long getHeartBeats() {

        double result =
                getDaysLived()
                        * 24.0
                        * 60.0
                        * LifeStatistics.HEART_BEATS_PER_MINUTE;

        return Math.round(result);
    }

    public double getBloodLiters() {

        return getDaysLived()
                * 24.0
                * 60.0
                * LifeStatistics.BLOOD_LITERS_PER_MINUTE;
    }

    public double getWaterLiters() {

        return getDaysLived()
                * LifeStatistics.WATER_LITERS_PER_DAY;
    }

    public long getLaughs() {

        double result =
                getDaysLived()
                        * LifeStatistics.LAUGHS_PER_DAY;

        return Math.round(result);
    }
}