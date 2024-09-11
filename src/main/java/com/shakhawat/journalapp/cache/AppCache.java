package com.shakhawat.journalapp.cache;

import com.shakhawat.journalapp.entity.ConfigJournal;
import com.shakhawat.journalapp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    private final ConfigJournalRepository configJournalRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournal> all = configJournalRepository.findAll();
        for (ConfigJournal configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
