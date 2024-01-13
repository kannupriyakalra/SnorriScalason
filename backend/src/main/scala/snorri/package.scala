package object snorri:
  // A lame try-with-resources equivalent
  def use[A <: AutoCloseable, B](resource: A)(code: A => B): B =
    try
      code(resource)
    finally
      resource.close()
