package a1ex9788.dadm.myquotations.databases;

import java.util.List;

import a1ex9788.dadm.myquotations.model.Quotation;

public interface QuotationDatabaseAccess {

    void addQuotation(Quotation quotation);

    void deleteQuotation(Quotation quotation);

    boolean existsQuotation(String quotationText);

    List<Quotation> getQuotations();

    void deleteAllQuotations();

}
