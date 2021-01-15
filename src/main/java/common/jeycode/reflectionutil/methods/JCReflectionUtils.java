package common.jeycode.reflectionutil.methods;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 
 * @author Javier Perez Alonso
 *
 *         21 jun. 2020
 *
 */
public interface JCReflectionUtils
{

						@SuppressWarnings("unchecked")
						default <T> T declaredBuild(final Class<?> anyClass,final int index,final Object...initargs)
																														throws SecurityException,ClassNotFoundException,InstantiationException,
																														IllegalAccessException,IllegalArgumentException,InvocationTargetException
						{
												final var constructor = Class.forName(anyClass.getName())
																																									.getDeclaredConstructors();
												constructor[index].setAccessible(true);
												return (T)constructor[index].newInstance(initargs);

						}

						/**
						 * 
						 * Method getValuesIntoArray
						 *
						 * @param list
						 * @param methodName
						 * @param e
						 * @return Array with the values
						 * @throws NoSuchMethodException
						 * @throws IllegalAccessException
						 * @throws IllegalArgumentException
						 * @throws InvocationTargetException
						 * @throws SecurityException
						 */
						default Object[] getValuesIntoArray(final List<?> list,final String[] methodName,final int e)
																														throws NoSuchMethodException,IllegalAccessException,IllegalArgumentException,
																														InvocationTargetException,SecurityException
						{
												final Class<?> instanceClass = list.get(e)
																																															.getClass();
												final Object instance = list.get(e);
												var methods = new Object[methodName.length];
												loadArrayOfData(methodName,instanceClass,instance,methods);
												return methods;
						}

						private void loadArrayOfData(final String[] methodName,final Class<?> instanceClass,final Object instance,
																																			Object[] methods) throws IllegalAccessException,InvocationTargetException,
																														NoSuchMethodException
						{
												for (var i = 0; i < methods.length; i++)
												{
																		methods[i] = instanceClass.getMethod(methodName[i],null)
																																												.invoke(instance,null);
												}
						}

}
