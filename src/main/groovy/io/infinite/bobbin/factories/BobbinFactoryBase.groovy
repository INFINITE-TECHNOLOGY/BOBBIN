package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import org.slf4j.helpers.Util

class BobbinFactoryBase extends BobbinFactoryAbstract{


    @Override
    BobbinConfig getBobbinConf() {
        Util.report("Using Bobbin default configuration:")
        BobbinConfig bobbinConfig = new BobbinConfig()//todo
        Util.report(new ObjectMapper().writeValueAsString(bobbinConfig))
        return bobbinConfig
    }
}
