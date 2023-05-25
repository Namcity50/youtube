CREATE OR REPLACE FUNCTION comment_like_count_function()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS
$$
BEGIN
  IF TG_OP = 'INSERT' THEN
    IF NEW.status = 'ROLE_LIKE' THEN
        UPDATE comment SET like_count = like_count + 1 WHERE id = NEW.comment_id;
    ELSE
        UPDATE comment SET dislike_count = dislike_count + 1 WHERE id = NEW.comment_id;
    END IF;

  ELSIF TG_OP = 'UPDATE' THEN
    IF OLD.status = 'ROLE_LIKE' AND NEW.status = 'ROLE_DISLIKE' THEN
       UPDATE comment SET like_count = like_count - 1,dislike_count = dislike_count+1 WHERE id = OLD.comment_id;
    ELSIF OLD.status = 'ROLE_DISLIKE' AND NEW.status = 'ROLE_LIKE' THEN
       UPDATE comment SET dislike_count = dislike_count + 1,like_count = like_count+1 WHERE id = OLD.comment_id;
    END IF;

  ELSIF TG_OP = 'DELETE' THEN
    IF OLD.status = 'ROLE_LIKE' THEN
        UPDATE comment SET like_count = like_count - 1 WHERE id = OLD.comment_id;
    ELSIF OLD.status = 'ROLE_DISLIKE' THEN
        UPDATE comment SET dislike_count = dislike_count - 1 WHERE id = OLD.comment_id;
    END IF;
  END IF;
RETURN NEW;
END;
$$;


CREATE TRIGGER comment_like_count_trigger
    BEFORE INSERT OR UPDATE OR DELETE
                     ON comment_like
                         FOR EACH ROW
                         EXECUTE PROCEDURE comment_like_count_function();