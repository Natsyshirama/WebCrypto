package model.crypto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CoursHistorique {
    private LocalDateTime datyUpdate;
    private BigDecimal cours;

    public LocalDateTime getDatyUpdate() {
        return datyUpdate;
    }

    public void setDatyUpdate(LocalDateTime datyUpdate) {
        this.datyUpdate = datyUpdate;
    }

    public BigDecimal getCours() {
        return cours;
    }

    public void setCours(BigDecimal cours) {
        this.cours = cours;
    }
}
