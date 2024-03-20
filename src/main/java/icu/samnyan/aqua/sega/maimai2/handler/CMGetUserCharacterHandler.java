package icu.samnyan.aqua.sega.maimai2.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import icu.samnyan.aqua.sega.maimai2.model.Mai2UserCharacterRepo;
import icu.samnyan.aqua.sega.general.BaseHandler;
import icu.samnyan.aqua.sega.maimai2.model.userdata.UserCharacter;
import icu.samnyan.aqua.sega.util.jackson.BasicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author samnyan (privateamusement@protonmail.com)
 */
@Component("Maimai2CMGetUserCharacterHandler")
public class CMGetUserCharacterHandler implements BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(CMGetUserCharacterHandler.class);

    private final BasicMapper mapper;

    private final Mai2UserCharacterRepo userCharacterRepository;

    public CMGetUserCharacterHandler(BasicMapper mapper, Mai2UserCharacterRepo userCharacterRepository) {
        this.mapper = mapper;
        this.userCharacterRepository = userCharacterRepository;
    }

    @Override
    public String handle(Map<String, Object> request) throws JsonProcessingException {
        long userId = ((Number) request.get("userId")).longValue();

        List<UserCharacter> userCharacterList = userCharacterRepository.findByUser_Card_ExtId(userId);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("returnCode", 1);
        resultMap.put("length", userCharacterList.size());
        resultMap.put("userCharacterList", userCharacterList);

        String json = mapper.write(resultMap);
        logger.info("Response: " + json);
        return json;
    }
}