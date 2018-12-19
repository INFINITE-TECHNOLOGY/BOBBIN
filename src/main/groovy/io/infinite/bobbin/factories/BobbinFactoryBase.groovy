package io.infinite.bobbin.factories

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.BobbinConfig
import org.slf4j.helpers.Util

class BobbinFactoryBase extends BobbinFactoryAbstract{


    @Override
    BobbinConfig getBobbinConf() {
        report("Using Bobbin default configuration:")
        BobbinConfig bobbinConfig = new BobbinConfig()//todo
        report(new ObjectMapper().writeValueAsString(bobbinConfig))
        return bobbinConfig
    }
}
