package zerobase.project3.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.project3.model.Company;
import zerobase.project3.model.Dividend;
import zerobase.project3.model.ScrapedResult;
import zerobase.project3.persist.CompanyRepository;
import zerobase.project3.persist.DividendRepository;
import zerobase.project3.persist.entity.CompanyEntity;
import zerobase.project3.persist.entity.DividendEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());
        List<Dividend> dividends = new ArrayList<>();
        for (var entity : dividendEntities) {
            dividends.add(Dividend.builder()
                    .date(entity.getDate())
                    .dividend(entity.getDividend())
                    .build());
        }
        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker()).
                name(company.getName()).build(),
                dividends);
    }
}
