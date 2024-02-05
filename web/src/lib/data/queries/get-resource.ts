type Error = {
  statusCode: number
  message: string
} | null

type ResourceData = {
  message: string
} | null

interface GetResource {
  (): Promise<[Error, ResourceData]>
}

export const getResource: GetResource = async () => {
  try {
    const resourcesResponse = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/resources`,
      {
        method: 'GET',
        cache: 'no-store',
        credentials: 'include',
      }
    )

    if (!resourcesResponse.ok) {
      const error: Error = {
        statusCode: resourcesResponse.status,
        message: await resourcesResponse.text(),
      }

      return [error, null]
    }

    const result: ResourceData = await resourcesResponse.json()

    return [null, result]
  } catch (error: any) {
    const errorData: Error = {
      statusCode: 500,
      message: error.message,
    }
    return [errorData, null]
  }
}
