package selim.enderrifts.misc;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.JsonContext;

public interface IJsonParser<T> {

	public T parse(JsonObject json, JsonContext ctx);

	public Class<T> getType();

}
