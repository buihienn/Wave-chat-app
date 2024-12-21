package com.wavechat.bus;

import com.wavechat.GlobalVariable;
import com.wavechat.dao.SpamReportDAO;
import com.wavechat.dto.SpamReportDTO;
import java.util.List;

public class SpamReportBUS {
    private SpamReportDAO spamReportDAO = new SpamReportDAO();

    // Hàm tạo 1 spam report
    public boolean addSpamReport(String reportedID) {
        String reporterID = GlobalVariable.getUserID(); 
        return spamReportDAO.addSpamReport(reporterID, reportedID);
    }
    public List<SpamReportDTO> getAllSpamReport() {
        return spamReportDAO.getAllSpamReport();
    }
}
