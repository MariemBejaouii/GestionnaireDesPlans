package entities;

import java.time.LocalDate;
import java.util.Date;
import javafx.scene.control.DatePicker;

/**
 *
 * @author LENOVO
 */
public class Plan {

    int idPlan, idUser;
    String designationPdff, Atelier, BT, NumeroDePlan, codeArticle;
    Date date;

    public Plan() {
    }

    public Plan(int idUser, String designationPdff, String Atelier, String BT, String NumeroDePlan, String codeArticle, Date date) {
        this.idUser = idUser;
        this.designationPdff = designationPdff;
        this.Atelier = Atelier;
        this.BT = BT;
        this.NumeroDePlan = NumeroDePlan;
        this.codeArticle = codeArticle;
        this.date = date;
    }

    public Plan(String designationPdff, String Atelier, String BT, String NumeroDePlan, String codeArticle, Date date) {
        this.designationPdff = designationPdff;
        this.Atelier = Atelier;
        this.BT = BT;
        this.NumeroDePlan = NumeroDePlan;
        this.codeArticle = codeArticle;
        this.date = date;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDesignationPdff() {
        return designationPdff;
    }

    public void setDesignationPdff(String designationPdff) {
        this.designationPdff = designationPdff;
    }

    public String getAtelier() {
        return Atelier;
    }

    public void setAtelier(String Atelier) {
        this.Atelier = Atelier;
    }

    public String getBT() {
        return BT;
    }

    public void setBT(String BT) {
        this.BT = BT;
    }

    public String getNumeroDePlan() {
        return NumeroDePlan;
    }

    public void setNumeroDePlan(String NumeroDePlan) {
        this.NumeroDePlan = NumeroDePlan;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Plan(int idPlan, int idUser, String designationPdff, String Atelier, String BT, String NumeroDePlan, String codeArticle, Date date) {
        this.idPlan = idPlan;
        this.idUser = idUser;
        this.designationPdff = designationPdff;
        this.Atelier = Atelier;
        this.BT = BT;
        this.NumeroDePlan = NumeroDePlan;
        this.codeArticle = codeArticle;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Plan{" + "designationPdff=" + designationPdff + ", Atelier=" + Atelier + ", BT=" + BT + ", NumeroDePlan=" + NumeroDePlan + ", codeArticle=" + codeArticle + ", date=" + date + '}';
    }

}
