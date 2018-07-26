package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.QuarterRepository;
import com.zmdev.goldenbag.service.QuarterService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuarterServiceImpl extends BaseServiceImpl<Quarter, Long, QuarterRepository> implements QuarterService {

    @Override
    public Quarter findCurrentQuarter() {
        return repository.findCurrentQuarter();
    }

    public Map<Integer, ArrayList<Quarter>> findAllByYear() {
        List<Quarter> quarters = repository.findAll();
        Map<Integer, ArrayList<Quarter>> res = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        for (Quarter quarter : quarters) {
            cal.setTime(quarter.getStartDate());
            int year = cal.get(Calendar.YEAR);
            List<Quarter> yearQuarters = res.get(year);
            if (yearQuarters == null) {
                ArrayList<Quarter> arr = new ArrayList<Quarter>();
                arr.add(quarter);
                res.put(year, arr);
            } else {
                yearQuarters.add(quarter);
            }
        }
        return res;
    }
}
