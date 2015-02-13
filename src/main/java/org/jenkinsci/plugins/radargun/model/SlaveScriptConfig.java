package org.jenkinsci.plugins.radargun.model;

public interface SlaveScriptConfig extends NodeScriptConfig {
    
    public int getSlaveIndex();
    
    public String getSlaveName();

    public SlaveScriptConfig withSlaveIndex(int slaveIndex);
    
    public SlaveScriptConfig withSlaveName(String slaveName);
    
    public static enum Options {
        SLAVE_INDEX(new Option("-i", "getSlaveIndex", true)), 
        SLAVE_NAME(new Option("-n", "getSlaveName", true));

        private Option option;

        private Options(Option option) {
            this.option = option;
        }

        public Option getOption() {
            return option;
        }
    }
}