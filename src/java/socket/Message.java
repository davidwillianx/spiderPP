/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author smartphonne
 */
public class Message {

    private JsonObject json;

    public Message(JsonObject json) {
        this.json = json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return this.json;
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        Json.createWriter(stringWriter).write(this.json);

        return stringWriter.toString();
    }
}
